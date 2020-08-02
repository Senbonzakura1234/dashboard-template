package com.app.manager.model.midware_model;

public class TestFormCheck {
    private boolean check1;
    private boolean check2;
    private boolean check3;
    private boolean check4;
    private String optionsRadios;
    private String optionsRadio2;

    public TestFormCheck() {
    }

    public TestFormCheck(boolean check1, boolean check2,
                         boolean check3, boolean check4,
                         String optionsRadios,
                         String optionsRadio2) {
        this.check1 = check1;
        this.check2 = check2;
        this.check3 = check3;
        this.check4 = check4;
        this.optionsRadios = optionsRadios;
        this.optionsRadio2 = optionsRadio2;
    }

    public boolean isCheck1() {
        return check1;
    }

    public void setCheck1(boolean check1) {
        this.check1 = check1;
    }

    public boolean isCheck2() {
        return check2;
    }

    public void setCheck2(boolean check2) {
        this.check2 = check2;
    }

    public boolean isCheck3() {
        return check3;
    }

    public void setCheck3(boolean check3) {
        this.check3 = check3;
    }

    public boolean isCheck4() {
        return check4;
    }

    public void setCheck4(boolean check4) {
        this.check4 = check4;
    }

    public String getOptionsRadios() {
        return optionsRadios;
    }

    public void setOptionsRadios(String optionsRadios) {
        this.optionsRadios = optionsRadios;
    }

    public String getOptionsRadio2() {
        return optionsRadio2;
    }

    public void setOptionsRadio2(String optionsRadio2) {
        this.optionsRadio2 = optionsRadio2;
    }
}
