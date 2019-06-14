package net.katagaitai.phpscan.php.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class PhpInteger extends PhpAnyType {
    @Getter
    private final long integer;

}
