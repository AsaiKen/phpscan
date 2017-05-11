<?php
require_once "define.php";
require_once "MultiLanguageFramework.php";
require_once "ScenarioEnvironment.php";
require_once "Smarty.class.php";
require_once "IPAToolView.php";

/**
 * AppGoatユーティリティクラス
 * AppGoat全体で使用する様々な機能を定義する
 * @category フレームワーク
 * @package	Framework
 */
class AppGoatUtil
{
	// ディレクトリ一覧取得除外オプション
	/** ファイルを除外する */
	const EXCLUDE_FILE = 0x00F;
	/** ディレクトリを除外する */
	const EXCLUDE_DIR = 0x0FF;
	/** 除外なし */
	const EXCLUDE_NONE = 0xFFF;

	// プロパティ名
	/** 言語 */
	const LANG_NAME = "lang";
	/** タイトル */
	const TITLE_NAME = "title";
	/**トークン*/
	const TOKEN_NAME = "_TOKEN";
	/** エラータイトル */
	const ERROR_TITLE_NAME = "error_title";
	/** エラーが発生しました */
	const ERROR_OCCURRED_NAME = "error_occurred";
	/** 最初のページに戻り、処理をやり直してください。 */
	const ERROR_TRY_AGAIN = "error_try_again";
	/** 最初のページに戻る */
	const ERROR_BACK_TO_TOP_PAGE = "error_back_to_top_page";
	/** エラーメッセージ */
	const ERROR_MESSAGE_NAME = "error_message";
	/** トップへ戻るリンクを表示するか */
	const ERROR_SHOW_TOP_PAGE_LINK = "error_show_top_page_link";

	/* トークンのデフォルト値 */
	const DEFAULT_TOKEN_VALUE = "0";

	/**
	 * セッション変数を初期化します
	 * @return void
	 */
	public static function initSession()
	{
		// 言語
		if (self::isNull($_SESSION[self::LANG_NAME])) {
			$_SESSION[self::LANG_NAME] = DEFAULT_LANGUAGE;
		}
	}

	/**
	 * 多言語化フレームワークのオブジェクトを取得します
	 * @return MultiLanguageFramework MultiLanguageFrameworkオブジェクト
	 */
	public static function getMultiLanguageFrameworkInstance()
	{
		// セッションがセットされていなければデフォルト言語を採用
		$lang = DEFAULT_LANGUAGE;
		if (!self::isNull($_SESSION[self::LANG_NAME])) {
			$lang = $_SESSION[self::LANG_NAME];
		}
		$lang_framework = new MultiLanguageFramework($lang);
		return $lang_framework;
	}

	/**
	 * Smartyのオブジェクトを取得します
	 * @param string $template_dir テンプレートディレクトリ (デフォルト=カレントディレクトリ)
	 * @return Smarty Smartyオブジェクト
	 */
	public static function getSmartyInstance($template_dir = "./")
	{
		$smarty = new Smarty();
		$smarty->template_dir = $template_dir;
		$smarty->compile_dir = SMARTY_COMPILE_DIR;
		$smarty->cache_dir = SMARTY_CACHE_DIR;
		$smarty->force_compile = true;
		return $smarty;
	}

	/**
	 * 値のNULLチェックを行う
	 * @param mixed $value 値
	 * @return boolean NULLまたは空白値ならtrue、値が入っていればfalse
	 */
	public static function isNull(&$value)
	{
		if (isset($value) && $value != "") {
			return false;
		}
		return true;
	}

	/**
	 * フォルダ内のファイル・ディレクトリ一覧を取得する<br>
	 * 除外フラグにEXCLUDE_DIRを指定すれば一覧からディレクトリを除外、EXCLUDE_FILEを指定すれば一覧からファイルを除外する
	 * @param string $path ディレクトリのパス
	 * @param int $ex_flg 除外フラグ
	 * @return array ファイル・ディレクトリのリスト
	 */
	public static function getDirectoryFileList($path, $ex_flg = self::EXCLUDE_NONE)
	{
		$list = array();
		$i = 0;
		$flg = $ex_flg & self::EXCLUDE_NONE;
		
		// 指定ディレクトリ内のファイル一覧を取得する
		if ($dir = opendir($path)) {
			while (($name = readdir($dir)) != false) {
				if ($name != "." && $name != "..") {
					$file = $path . DIRECTORY_SEPARATOR . $name;
					if (is_dir($file) && ($flg != self::EXCLUDE_DIR)) {
						// ディレクトリを除外しないとき
						$list[$i++] = $name;
					}

					if (is_file($file) && ($flg != self::EXCLUDE_FILE)) {
						// ファイルを除外しないとき
						$list[$i++] = $name;
					}
				}
			}
			// ハンドルをクローズする 
			closedir($dir);
		}
		return $list;
	}

	/**
	 * Commonディレクトリのパスを取得する
	 * @return string Scenarios/Commonディレクトリのパス
	 */
	public static function getScenarioCommonDirectoryPath()
	{
		return SCENARIO_COMMON_DIR;
	}

	/**
	 * 脆弱ウェブアプリケーション（IFRAME内）の共通エラー画面を表示する
	 * @param int $error_code エラーコード
	 * @return void
	 */
	public static function displayVulnWebApplicationError($error_code)
	{
		$view = new IPAToolView(SMARTY_TEMPLATE_DIR, VULSOFT_COMMON_ERROR_TEMPLATE);
		$contents = array();
		// 共通メッセージを取得
		try {
			$language_framework = self::getMultiLanguageFrameworkInstance();
			$contents = $language_framework->getCommonMessage();
			// エラーコンテンツ割り当て
			$contents = self::getErrorContents($error_code, $contents, true);
		} catch (Exception $e) {
			// 言語ファイルの取得でエラー発生
			LogUtil::errorLog(__FILE__, $e->getCode(), $e->getMessage());
			$contents = self::getErrorContents($e->getCode(), $contents, true);
		}
		// エラー画面表示
		$view->display($contents);
	}

	/**
	 * エラーページ表示時のコンテンツを取得する
	 * @param int $error_code エラーコード
	 * @param array $contents 共通メッセージのコンテンツ
	 * @param boolean $vulsoft_flg 脆弱ウェブアプリケーションからの呼出かどうか
	 * @return array エラー用のコンテンツ
	 */
	public static function getErrorContents($error_code, $contents, $vulsoft_flg)
	{
		$error_contents = array();
		$error_message = null;

		// エラーコードにより割り当てメッセージを決定
		switch ($error_code) {
			case LogUtil::ERROR_COMMON_LANGUAGE_FILE_NOT_FOUND:
			case LogUtil::ERROR_COMMON_LANGUAGE_FILE_INVALID_FORMAT:
				// 共通言語ファイルのエラーの場合多言語化できないため、デフォルトのメッセージを割り当て
				$error_contents[self::ERROR_TITLE_NAME] = ERROR_MESSAGE_TITLE;
				$error_contents[self::ERROR_OCCURRED_NAME] = ERROR_MESSAGE_ERROR_OCCURRED;
				$error_contents[self::ERROR_TRY_AGAIN] = ERROR_MESSAGE_PLEASE_TRY_AGAIN;
				$error_contents[self::ERROR_BACK_TO_TOP_PAGE] = ERROR_MESSAGE_BACK_TO_TOP_PAGE;
				if ($error_code == LogUtil::ERROR_COMMON_LANGUAGE_FILE_NOT_FOUND) {
					// 共通メッセージファイルが見つからない
					$error_message = ERROR_MESSAGE_LANGUAGE_FILE_NOT_FOUND;
				} else {
					// 共通メッセージファイルのフォーマットが不正
					$error_message = ERROR_MESSAGE_LANGUAGE_FILE_INVALID_FORMAT;
				}
				$error_contents[self::ERROR_MESSAGE_NAME] = $error_message;
				$error_contents[self::ERROR_SHOW_TOP_PAGE_LINK] = $vulsoft_flg;
				break;
			default:
				// 通常の場合、共通メッセージをまず割り当て
				$error_contents = $contents;
				// エラーコードからエラーコード名を取得
				$errro_code_name = LogUtil::getErrorCodeName($error_code);
				// エラーページのコンテンツを割り当て
				$error_contents[self::ERROR_MESSAGE_NAME] = $contents[$errro_code_name];
				$error_contents[self::ERROR_SHOW_TOP_PAGE_LINK] = true;
				break;
		}
		return $error_contents;
	}

	/**
	 * トークンを取得する
	 * @return string トークン
	 */
	public static function getToken()
	{
		$token = md5((string) mt_rand());
		return $token;
	}

	/**
	 * セッションにトークンをセットする
	 * @param object|string $token_key トークンのキー
	 * @param string $token トークンの値
	 * @return void
	 */
	public static function setToken($token_key, $token)
	{
		// トークンをセット
		$token_key_name = self::getTokenKey($token_key);
		$_SESSION[$token_key_name] = $token;
	}

	/**
	 * トークンをチェックする。指定したトークンがセッションに登録されているトークンと同じ値ならエラーとする。
	 * @param string|object $token_key トークンのキー
	 * @param string $token トークンの値
	 * @return boolean trueまたはfalse
	 */
	public static function checkToken($token_key, &$token)
	{
		$token_key_name = self::getTokenKey($token_key);
		if (self::isNull($token)) {
			// トークンがセットされていない場合、デフォルトの値をセット
			$token = self::DEFAULT_TOKEN_VALUE;
		}

		if ((isset($_SESSION[$token_key_name]) && isset($token)) && ($_SESSION[$token_key_name] == $token)) {
			// トークンが同じならNG
			return false;
		}
		return true;
	}

	/**
	 * トークンをクリアする
	 * @param string|object $token_key トークンのキー
	 * @return void
	 */
	public static function clearToken($token_key)
	{
		$token_key_name = self::getTokenKey($token_key);
		unset($_SESSION[$token_key_name]);
	}

	/**
	 * トークンのキーを取得する。引数がオブジェクト型ならキーはそのクラス名とする。
	 * @param string|object $token_key 文字列またはオブジェクト
	 * @return string トークンのキー
	 */
	public static function getTokenKey($token_key)
	{
		$token_key_name = $token_key;
		if (is_object($token_key)) {
			$token_key_name = get_class($token_key);
		}
		$token_key_name .= self::TOKEN_NAME;
		return $token_key_name;
	}
}
?>