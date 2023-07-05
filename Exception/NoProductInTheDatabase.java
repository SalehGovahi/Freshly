package com.example.freshly.Exception;

public class NoProductInTheDatabase extends Exception{
    public NoProductInTheDatabase(String msg){
        super(msg);
    }
    public NoProductInTheDatabase(){
        super();
    }
}
