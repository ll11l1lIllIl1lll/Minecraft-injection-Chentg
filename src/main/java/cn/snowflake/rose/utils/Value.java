package cn.snowflake.rose.utils;


import java.util.ArrayList;
import java.util.List;

public class Value<T> {
    private T value;
    private T valueMin;
    private T valueMax;
    private double step;
    private final T defaultValue;
    private final String name;
    public boolean isValueBoolean;
    public boolean isValueInteger;
    public boolean isValueFloat;
    public boolean isValueDouble;
    public boolean isValueMode;
    public boolean isValueLong;
    public boolean isValueByte;
    private int current;
    public ArrayList<String> mode;
    public double sliderX;
    public boolean set;
    public static final List<Value> list;
    public boolean isSettingMode;
    public boolean openMods;
    public double maxSliderSize;
    public final int RADIUS = 4;
    public float currentRadius;
    public boolean disabled;
    private String modeTitle;


    static {
        list = new ArrayList<Value>();
    }


    public Value(final String classname, final String modeTitle, final int current) {
        this.set = false;
        this.currentRadius = 4.0f;
        this.isValueBoolean = false;
        this.isValueInteger = false;
        this.isValueFloat = false;
        this.isValueDouble = false;
        this.isValueLong = false;
        this.isValueByte = false;
        this.defaultValue = this.value;
        this.isValueMode = true;
        this.step = 0.1;
        this.mode = new ArrayList<String>();
        this.current = current;
        this.name = String.valueOf(classname) + "_" + "Mode";
        this.modeTitle = modeTitle;
        Value.list.add(this);
    }



    public Value(final String name, final T defaultValue, final T valueMin, final T valueMax) {
        this.set = false;
        this.currentRadius = 4.0f;
        this.isValueBoolean = false;
        this.isValueInteger = false;
        this.isValueFloat = false;
        this.isValueDouble = false;
        this.isValueLong = false;
        this.isValueByte = false;
        this.defaultValue = this.value;
        this.name = name;
        this.value = defaultValue;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.step = 0.1;
        if (this.value instanceof Double) {
            this.isValueDouble = true;
        }
        Value.list.add(this);
    }

    private T text ;
    private String  tips;
    public boolean isValueString;


    public Value(String name, String tips, final T text){
        this.set = false;
        this.currentRadius = 4.0f;
        this.isValueBoolean = false;
        this.isValueInteger = false;
        this.isValueFloat = false;
        this.isValueDouble = false;
        this.isValueLong = false;
        this.isValueByte = false;
        this.isValueString = true;

        this.name = name;
        this.defaultValue = text;
        this.text = text;
        this.tips = tips;
        Value.list.add(this);
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
    public void setText(T text) {
        this.text = text;
    }

    public T getText() {
        return text;
    }

    public Value(final String name, final T value, final T valueMin, final T valueMax, final double steps) {
        this.set = false;
        this.currentRadius = 4.0f;
        this.isValueBoolean = false;
        this.isValueInteger = false;
        this.isValueFloat = false;
        this.isValueDouble = false;
        this.isValueLong = false;
        this.isValueByte = false;
        this.defaultValue = value;
        this.name = name;
        this.value = value;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.step = steps;
        if (value instanceof Double) {
            this.isValueDouble = true;
        }
        Value.list.add(this);
    }

    public Value(final String name, final T value) {
        this.set = false;
        this.currentRadius = 4.0f;
        this.isValueBoolean = false;
        this.isValueInteger = false;
        this.isValueFloat = false;
        this.isValueDouble = false;
        this.isValueLong = false;
        this.isValueByte = false;
        this.defaultValue = value;
        this.name = name;
        this.value = value;
        if (value instanceof Boolean) {
            this.isValueBoolean = true;
        }
        else if (value instanceof Integer) {
            this.isValueInteger = true;
        }
        else if (value instanceof Float) {
            this.isValueFloat = true;
        }
        else if (value instanceof Long) {
            this.isValueLong = true;
        }
        else if (value instanceof Byte) {
            this.isValueByte = true;
        }
        Value.list.add(this);
    }

    public Value(final String name, final String name2, final String nam3, final T value, final T value2, final T value3) {
        this.set = false;
        this.currentRadius = 4.0f;
        this.isValueBoolean = false;
        this.isValueInteger = false;
        this.isValueFloat = false;
        this.isValueDouble = false;
        this.isValueLong = false;
        this.isValueByte = false;
        this.defaultValue = value;
        this.name = name;
        this.value = value;
        if (value instanceof Boolean) {
            this.isValueBoolean = true;
        }
        else if (value instanceof Integer) {
            this.isValueInteger = true;
        }
        else if (value instanceof Float) {
            this.isValueFloat = true;
        }
        else if (value instanceof Double) {
            this.isValueDouble = true;
        }
        else if (value instanceof Long) {
            this.isValueLong = true;
        }
        else if (value instanceof Byte) {
            this.isValueByte = true;
        }
        Value.list.add(this);
    }

    public void addValue(final String valueName) {
        this.mode.add(valueName);
    }

    public void setCurrentMode(final int current) {
        if (current > this.mode.size() - 1) {
            System.out.println("Value is to big! Set to 0. (" + this.mode.size() + ")");
            return;
        }
        this.current = current;
    }

    public int getCurrentMode() {
        return this.current;
    }

    public String getModeName() {
        return this.getModeAt(this.getCurrentMode());
    }

    public ArrayList<String> listModes() {
        return this.mode;
    }

    public String getModeTitle() {
        return this.modeTitle;
    }

    public String getModeAt(final int index) {
        return this.mode.get(index);
    }

    public String getModeAt(final String modeName) {
        for (int i = 0; i < this.mode.size(); ++i) {
            if (this.mode.get(i).equalsIgnoreCase(modeName)) {
                return this.mode.get(i);
            }
        }
        return "NULL";
    }

    public int getModeInt(final String modeName) {
        for (int i = 0; i < this.mode.size(); ++i) {
            if (this.mode.get(i).equalsIgnoreCase(modeName)) {
                return i;
            }
        }
        return 0;
    }

    public boolean isCurrentMode(final String modeName) {
        return this.getModeAt(this.getCurrentMode()).equalsIgnoreCase(modeName);
    }

    public String getAllModes() {
        String all = "";
        for (int i = 0; i < this.mode.size(); ++i) {
            all = String.valueOf(all) + this.mode.get(i).toString();
        }
        return all;
    }

    public final String getValueName() {
        return this.name;
    }

    public String getDisplayTitle() {
        if (this.isValueMode) {
            return this.getModeTitle();
        }
        return this.getValueName().split("_")[1];
    }

    public final T getValueMin() {
        if (this.value instanceof Double) {
            return this.valueMin;
        }
        return null;
    }

    public final double getSteps() {
        return this.step;
    }

    public final T getValueMax() {
        if (this.value instanceof Double) {
            return this.valueMax;
        }
        return null;
    }

    public final T getDefaultValue() {
        return this.defaultValue;
    }

    public final T getValueState() {
        return this.value;
    }

    public final void setValueState(final T value) {
        this.value = value;
    }

    public static Value getBooleanValueByName(final String name) {
        for (final Value value : Value.list) {
            if (value.isValueBoolean && value.getValueName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public static Value getDoubleValueByName(final String name) {
        for (final Value value : Value.list) {
            if (value.isValueDouble && value.getValueName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public static Value getModeValue(final String valueName, final String title) {
        for (final Value value : Value.list) {
            if (value.isValueMode && value.getValueName().equalsIgnoreCase(valueName) && value.getModeTitle().equalsIgnoreCase(title)) {
                return value;
            }
        }
        return null;
    }

    public ArrayList<String> getModes() {
        return mode;
    }
}

