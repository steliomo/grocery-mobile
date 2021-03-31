package mz.co.commandline.grocery.generics.dto;

public class EnumDTO {

    private String value;

    private String label;

    public EnumDTO(final String value, final String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {  
        return getLabel();
    }
}
