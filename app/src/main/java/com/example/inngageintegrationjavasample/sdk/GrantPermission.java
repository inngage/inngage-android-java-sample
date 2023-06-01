package com.example.inngageintegrationjavasample.sdk;

public abstract class GrantPermission {
    protected abstract void call(int requestCode, String permissions[], int[] grantResults);
}