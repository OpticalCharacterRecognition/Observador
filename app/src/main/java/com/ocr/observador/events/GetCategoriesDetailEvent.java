package com.ocr.observador.events;

/**
 * Created by Vazh on 25/5/2015.
 */
public class GetCategoriesDetailEvent extends AbstractEvent {
    public enum Type {
        COMPLETED,
        STARTED
    }

    private int _resultCode;


    public GetCategoriesDetailEvent(Type type, int resultCode) {
        super(type);
        this._resultCode = resultCode;

    }

    public int getResultCode() {
        return _resultCode;
    }

}
