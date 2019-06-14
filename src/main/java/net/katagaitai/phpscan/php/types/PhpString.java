package net.katagaitai.phpscan.php.types;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PhpString extends PhpAnyType {
    @Getter
    private String string;
}
