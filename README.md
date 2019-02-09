# PHPSCAN

PHPSCAN is a security analysis tool for PHP scripts, which detects vulnerabilities by simulating PHP interpreter.

PHPSCAN has following features.

* supports the object-oriented-programming feature of PHP.
* supports the reflection feature of PHP.
* avoid false-positive by checking whether escape process exists or not.

PHPSCAN can detect following vulnerabilities.

* Remote Code Execution
* Local File Inclusion
* SQL Injection
* Path Manipulation
* Server Side Request Forgery
* Cross Site Scripting
* Object Injection
* PHP File Manipulation
* XML External Entity

PHPSCAN outputs the HTML-format report of the scan result in the "./result" directory.
In this report, you can know the path from the point where a user-input is set to a variable to the point where a dangerous functon uses a variable. 

Environment
---

* Windows 7 64bit
* jdk 1.8

Build
---

No need to build. 
You can use "phpscan.jar" in the root of this repository.

If you want to build, run 

```
ant
```

This generates "phpscan.jar".

Prepare
---

create a folder, and copy followings into it.

* phpscan.jar
* setting.properties
* start_scan.bat
* resource/

Execute
---

For example, if you want to scan a plugin of a CMS, 

1. Open the "setting.properties" in your favorite text-editor.
2. Write the path of the plugin directory to "ENTRY_POINT_PATH" entry.
3. Write the path of the CMS directory to "PROJECT_PATH" entry. 
4. Write the path of the "php.ini" file to "PHP_INI_PATH".
5. Save the change, and close the text-editor.
5. Run the "start_scan.bat" to start a scan.
6. If vulnerabilities exist, PHPSCAN generates a scan report in the "./result" directory.

Settings
---

You can change the scan setting by changing the "setting.properties" file.  

#### ENTRY_POINT_PATH

Specify the path of the directory that contains PHP script files that you want to scan.

You must specify either ENTRY_POINT_PATH or ENTRY_POINT_PARENT_PATH.

#### ENTRY_POINT_PARENT_PATH

Use this entry when more than one ENTRY_POINT_PATH exist.
You can scan multiple ENTRY_POINT_PATH all at once by deploying ENTRY_POINT_PATH directories into the one directory and specifying it to this entry.
For example, if you want to scan multiple plugins of CMS all at once, specify the plugin root directory to this entry. 

You must specify either ENTRY_POINT_PATH or ENTRY_POINT_PARENT_PATH.

#### PROJECT_PATH

This entry is optional.

Specify the path of the directory that contains PHP script files required for executing scan targets.
PHP script files in this directory are excluded from a scan.
For example, if you want to scan a plugin of CMS, specify the CMS root directory to this entry.

If this entry is empty, either ENTRY_POINT_PATH or ENTRY_POINT_PARENT_PATH is used to this entry.

#### PHP_INI_PATH

Specify the path of the "php.ini" file.

You must specify this entry.

#### IGNORE_TXT_PATH

This entry is optional.

Specify the path of the text file that lists paths of PHP script files that you want to exclude for a scan.


#### IGNORE_REGEXPS

This entry is optional.

Specify paths of PHP script files in "regular expression" manners that you want to exclude for a scan.   
You can specify multiple values in "comma separated" manners.

#### SQL_ESCAPE_FUNCTIONS

This entry is optional.

Specify names of SQL escape functions if the application implements handcrafted functions to escape SQL statements. 
You can specify multiple values in "comma separated" manners.

#### HTML_ESCAPE_FUNCTIONS

This entry is optional.

Specify names of HTML escape functions if the application implements handcrafted functions to escape HTML.
You can specify multiple values in "comma separated" manners.

#### DISABLED_VULNERABILITY_CATEGORIES

This entry is optional.

Specify vulnerability categories that you want to exclude for a scan.

Available values are followings.

* Remote Code Execution
* Local File Inclusion
* SQL Injection
* Path Manipulation
* Server Side Request Forgery
* Cross Site Scripting
* Object Injection
* PHP File Manipulation

You can specify multiple values in "comma separated" manners.  

#### PHP_FILE_EXTENSIONS

This entry is optional.

Specify extensions that are interpreted as PHP script files.
You can specify multiple values in "comma separated" manners.  

If this entry is empty, "php" and "inc" are used to this entry.

#### USED_FRAMEWORKS

This entry is optional.

Specify names of CMS and framework used in the application.
By specifying this entry, a scan coverage can be increased.

Available values are followings.

* WordPress

You can specify multiple values in "comma separated" manners.  

Detected vulnerabilities in real
---

All items listed in the following URL related to PHP were detected by PHPSCAN.

[Japan Vulnerability Notes](http://jvndb.jvn.jp/search/index.php?mode=_vulnerability_search_IA_VulnSearch&lang=en&useSynonym=1&keyword=ASAI%20Ken)

Others
---

- Not supports new features introduced in PHP 7.

License
---

This software is released under the MIT License, see LICENSE.txt.  
