package com.buildpc.models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Build implements INotifier{
    private CPU processor;
    private Part ram;
    private Part hd;
    private Part opticalDrive;
    private Part gpu;
    private Part os;
    private HashMap<String, Part> apps = new HashMap<>();

    private List<IWatcher> watchers = new ArrayList<IWatcher>();

    public CPU getProcessor() {
        return processor;
    }

    public void setProcessor(CPU processor) {
        this.processor = processor;
    }

    public Part getRam() {
        return ram;
    }

    public void setRam(Part ram) {
        this.ram = ram;
    }

    public Part getHd() {
        return hd;
    }

    public void setHd(Part hd) {
        this.hd = hd;
    }

    public Part getOpticalDrive() {
        return opticalDrive;
    }

    public void setOpticalDrive(Part opticalDrive) {
        this.opticalDrive = opticalDrive;
    }

    public Part getGpu() {
        return gpu;
    }

    public void setGpu(Part gpu) {
        this.gpu = gpu;
    }

    public Part getOs() {
        return os;
    }

    public void setOs(Part os) {
        this.os = os;
    }

    public List<Part> getApps() {
        List<Part> parts = new ArrayList<>(apps.values());
        return parts;
    }

    public void addApp(String name, Part app) {
        apps.put(name, app);
    }

    public void remove(String name) {
        apps.remove(name);
    }

    //Get totalPrice of all parts with value
    public double getTotalPrice() {
        double totalPrice = 0;
        totalPrice += getAllParts().stream().mapToDouble(Part::getPrice).sum();
        if (processor.getMake() != null) {
            if (processor.getMake().equals("Intel")) {
                totalPrice += 499;
            } else if (processor.getMake().equals("AMD")) {
                totalPrice += 599;
            }
        }
        return totalPrice;
    }

    //Get all parts with value
    public List<Part> getAllParts() {
        Field[] fields = this.getClass().getDeclaredFields();
        List<Part> parts = new ArrayList<Part>();
        try {
            for (Field f: fields) {
                f.setAccessible(true);
                if (Part.class.isAssignableFrom(f.getType())) {
                    Part p = (Part) f.get(this);
                    if (p != null) {
                        parts.add(p);
                    }
                } else if (HashMap.class.isAssignableFrom((f.getType()))) {
                    HashMap hm = (HashMap) f.get(this);
                    List<Part> partList = new ArrayList<Part>(hm.values());
                    if (parts != null)
                        partList.forEach(p -> parts.add(p));
                }
            }
        } catch (Exception ex) {

        }
        return parts;
    }

    @Override
    public void addWatcher(IWatcher w) {
        watchers.add(w);
    }

    @Override
    public void removeWatcher(IWatcher w) {
        watchers.remove(w);
    }

    @Override
    public void notifyWatchers() {
        watchers.stream().forEach(w -> w.update());
    }
}
