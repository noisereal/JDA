package net.dv8tion.jda.internal.interactions.component;

import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextInputImpl implements TextInput
{

    private final String id;
    private final TextInputStyle style;
    private final String label;

    private int minLength = -1;
    private int maxLength = -1;

    private boolean required;
    private String value;
    private String placeholder;

    public TextInputImpl(DataObject object)
    {
        this(
            object.getString("custom_id"),
            TextInputStyle.fromKey(object.getInt("style", -1)),
            object.getString("label", null),
            object.getInt("min_Length", -1),
            object.getInt("max_length", -1),
            object.getBoolean("required", false),
            object.getString("value", null),
            object.getString("placeholder", null)
        );
    }

    public TextInputImpl(
            String id, TextInputStyle style, String label, int minLength, int maxLength, boolean required,
            String value, String placeholder)
    {
        this.id = id;
        this.style = style;
        this.label = label;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.required = required;
        this.value = value;
        this.placeholder = placeholder;
    }

    public TextInputImpl(String id, TextInputStyle style, String label)
    {
        this.id = id;
        this.style = style;
        this.label = label;
    }

    public void setRequired(boolean required)
    {
        this.required = required;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setPlaceholder(String placeholder)
    {
        this.placeholder = placeholder;
    }

    @NotNull
    @Override
    public Type getType()
    {
        return Type.TEXT_INPUT;
    }

    @NotNull
    @Override
    public TextInputStyle getStyle()
    {
        return style;
    }

    @NotNull
    @Override
    public String getId()
    {
        return id;
    }

    @Nullable
    @Override
    public String getLabel()
    {
        return label;
    }

    @Override
    public int getMinLength()
    {
        return minLength;
    }

    @Override
    public int getMaxLength()
    {
        return maxLength;
    }

    @Override
    public boolean isRequired()
    {
        return required;
    }

    @Nullable
    @Override
    public String getValue()
    {
        return value;
    }

    @Nullable
    @Override
    public String getPlaceHolder()
    {
        return placeholder;
    }

    @NotNull
    @Override
    public DataObject toData()
    {
        DataObject obj = DataObject.empty()
                    .put("type", 4)
                    .put("custom_id", id)
                    .put("style", style.getKey())
                    .put("label", label)
                    .put("required", required);
        if (minLength != -1)
            obj.put("min_length", minLength);
        if (maxLength != -1)
            obj.put("max_length", maxLength);
        if (required)
            obj.put("required", true);
        if (value != null)
            obj.put("value", value);
        if (placeholder != null)
            obj.put("placeholder", placeholder);
        return obj;
    }
}
