package de.minestar.core.data.values;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.server.NBTTagCompound;

public class ValueBoolean implements IValue {

    private static final String name = "BOOLEAN";
    private ConcurrentHashMap<String, Boolean> valueList = new ConcurrentHashMap<String, Boolean>();

    @Override
    public Object getValue(String name) {
        return this.valueList.get(name);
    }

    @Override
    public void setValue(String name, Object value) {
        this.valueList.put(name, (Boolean) value);
    }

    @Override
    public void load(NBTTagCompound NBTTag) {
        if (NBTTag.hasKey(name)) {
            NBTTagCompound thisCompound = NBTTag.getCompound(name);
            for (Object base : thisCompound.d()) {
                if (base instanceof NBTTagCompound) {
                    NBTTagCompound thisTag = (NBTTagCompound) base;
                    this.valueList.put(thisTag.getName(), thisTag.getBoolean(thisTag.getName()));
                }
            }
        }
    }

    @Override
    public void save(NBTTagCompound NBTTag) {
        NBTTagCompound thisTag = new NBTTagCompound();
        for (Map.Entry<String, Boolean> entry : this.valueList.entrySet()) {
            thisTag.setBoolean(entry.getKey(), entry.getValue());
        }
        NBTTag.setCompound(name, thisTag);
    }

}