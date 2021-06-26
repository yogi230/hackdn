package com.example.hackedin;

public  class nextTimeCalc {
    public  int emailType;
    public long current;
    public  long next;

    public nextTimeCalc(int emailType, long currentTime) {
        this.emailType=emailType;
        this.current=current;
        next=0;
    }

    public long getNextTime()
    {
        switch(emailType)
        {
            case 0:
                next=current+30L;
                break;
            case 1:
                next=current+604800L;
                break;
            case 2:
                next=current+18144000L;
                break;
            case 3:
                next=current+18364752000L;
                break;
        }
        return next;
    }
}