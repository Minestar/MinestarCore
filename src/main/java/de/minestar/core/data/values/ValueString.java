package de.minestar.core.data.values;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.server.NBTTagCompound;

public class ValueString implements IValue {

    private static final String name = "STRING";
    private ConcurrentHashMap<String, String> valueList = new ConcurrentHashMap<String, String>();

    @Override
    public Object getValue(String name) {
        return this.valueList.get(name);
    }

    @Override
    public void setValue(String name, Object value) {
        this.valueList.put(name, (String) value);
    }

    @Override
    public void load(NBTTagCompound NBTTag) {
        if (NBTTag.hasKey(name)) {
            NBTTagCompound thisCompound = NBTTag.getCompound(name);
            for (Object base : thisCompound.d()) {
                if (base instanceof NBTTagCompound) {
                    NBTTagCompound thisTag = (NBTTagCompound) base;
                    this.valueList.put(thisTag.getName(), thisTag.getString(thisTag.getName()));
                }
            }
        }
    }

    @Override
    public void save(NBTTagCompound NBTTag) {
        NBTTagCompound thisTag = new NBTTagCompound();
        for (Map.Entry<String, String> entry : this.valueList.entrySet()) {
            thisTag.setString(entry.getKey(), entry.getValue());
        }
        NBTTag.setCompound(name, thisTag);
    }

}