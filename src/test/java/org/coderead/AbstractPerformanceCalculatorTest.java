package org.coderead;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author dengzhe
 * @date 2021/4/11
 */
public class AbstractPerformanceCalculatorTest{

    @Rule
    public ExpectedException exceptedException = ExpectedException.none();

    @Test
    public void should_throw_exception(){
        exceptedException.expect(IllegalArgumentException.class);
        exceptedException.expectMessage("wrong type");
        AbstractPerformanceCalculator.of("no type valid");
    }

}