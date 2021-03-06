package com.ocr.observador.events;

/**
 * Created by Vazh on 25/5/2015.
 */
public class MarkerClickedEvent extends AbstractEvent {
    public enum Type {
        COMPLETED,
        STARTED
    }

    private int _resultCode;

    private String _markerId;


    public MarkerClickedEvent(Enum type, int _resultCode, String _markerId) {
        super(type);
        this._resultCode = _resultCode;
        this._markerId = _markerId;
    }

    public String getMarkerId() {
        return _markerId;
    }

    public int getResultCode() {
        return _resultCode;
    }

}
