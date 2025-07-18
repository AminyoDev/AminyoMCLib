package dev.aminyo.aminyomclib.core.utils.colors.patterns.utils.colors.patterns;

/**
 * Represents a color pattern which can be applied to a String.
 */
public interface Pattern {

    /**
     * Applies this pattern to the provided String.
     * Output might me the same as the input if this pattern is not present.
     *
     * @param string The String to which this pattern should be applied to
     * @return The new String with applied pattern
     */
    String process(String string);

}
