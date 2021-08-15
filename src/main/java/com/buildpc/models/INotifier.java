package com.buildpc.models;

public interface INotifier {
    public void addWatcher(IWatcher w);

    public void removeWatcher(IWatcher w);

    public void notifyWatchers();
}
