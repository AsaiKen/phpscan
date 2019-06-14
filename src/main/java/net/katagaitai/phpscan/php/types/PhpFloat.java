package net.katagaitai.phpscan.php.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class PhpFloat extends PhpAnyType {
    @Getter
    private final double float_;
}
