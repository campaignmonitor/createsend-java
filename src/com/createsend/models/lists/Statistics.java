/**
 * Copyright (c) 2011 Toby Brain
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package com.createsend.models.lists;

public class Statistics {  
    public int TotalActiveSubscribers;
    public int NewActiveSubscribersToday;
    public int NewActiveSubscribersYesterday;
    public int NewActiveSubscribersThisWeek;
    public int NewActiveSubscribersThisMonth;
    public int NewActiveSubscribersThisYear;
    public int TotalUnsubscribes;
    public int UnsubscribesToday;
    public int UnsubscribesYesterday;
    public int UnsubscribesThisWeek;
    public int UnsubscribesThisMonth;
    public int UnsubscribesThisYear;
    public int TotalDeleted;
    public int DeletedToday;
    public int DeletedYesterday;
    public int DeletedThisWeek;
    public int DeletedThisMonth;
    public int DeletedThisYear;

    @Override
    public String toString() {
        return String.format("TotalActiveSubscribers: %s, NewActiveSubscribersToday: %s, NewActiveSubscribersYesterday: %s, NewActiveSubscribersThisWeek: %s, NewActiveSubscribersThisMonth: %s, NewActiveSubscribersThisYear: %s, TotalUnsubscribes: %s, UnsubscribesToday: %s, UnsubscribesYesterday: %s, UnsubscribesThisWeek: %s, UnsubscribesThisMonth: %s, UnsubscribesThisYear: %s, TotalDeleted: %s, DeletedToday: %s, DeletedYesterday: %s, DeletedThisWeek: %s, DeletedThisMonth: %s, DeletedThisYear: %s, TotalBounces: %s, BouncesToday: %s, BouncesYesterday: %s, BouncesThisWeek: %s, BouncesThisMonth: %s, BouncesThisYear: %s",
            TotalActiveSubscribers, NewActiveSubscribersToday, NewActiveSubscribersYesterday,
            NewActiveSubscribersThisWeek, NewActiveSubscribersThisMonth, NewActiveSubscribersThisYear, 
            TotalUnsubscribes, UnsubscribesToday, UnsubscribesYesterday, UnsubscribesThisWeek, 
            UnsubscribesThisMonth, UnsubscribesThisYear, TotalDeleted, DeletedToday,
            DeletedYesterday, DeletedThisWeek, DeletedThisMonth, DeletedThisYear, TotalBounces, 
            BouncesToday, BouncesYesterday, BouncesThisWeek, BouncesThisMonth, BouncesThisYear);
    }
    public int TotalBounces;
    public int BouncesToday;
    public int BouncesYesterday;
    public int BouncesThisWeek;
    public int BouncesThisMonth;
    public int BouncesThisYear;
}
