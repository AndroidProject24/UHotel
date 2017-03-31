package com.acuteksolutions.uhotel.mvp.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by toan.it
 * Date: 24/05/2016
 */
public class JsonArray<T> {
    @Expose
    public List<T> result;
}
