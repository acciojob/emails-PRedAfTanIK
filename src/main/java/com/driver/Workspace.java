package com.driver;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class Workspace extends Gmail{

    private ArrayList<Meeting> calendar; // Stores all the meetings

    public Workspace(String emailId) {
        // The inboxCapacity is equal to the maximum value an integer can store.
        super(emailId,Integer.MAX_VALUE);
        calendar = new ArrayList<>();
    }

    public void addMeeting(Meeting meeting){
        //add the meeting to calendar
        calendar.add(meeting);
    }

    public int findMaxMeetings(){
        // find the maximum number of meetings you can attend
        // 1. At a particular time, you can be present in at most one meeting
        // 2. If you want to attend a meeting, you must join it at its start time and leave at end time.
        // Example: If a meeting ends at 10:00 am, you cannot attend another meeting starting at 10:00 am
        if(calendar.size()<=1) return calendar.size();

        Collections.sort(calendar,(a,b)-> a.getStartTime().compareTo(b.getStartTime())!=0?
                                            a.getStartTime().compareTo(b.getStartTime()):
                                            a.getEndTime().compareTo(b.getEndTime()));

        int[] dp = new int[calendar.size()];
        Arrays.fill(dp,1);
        int max = 0;

        for(int i=0;i<calendar.size();i++){
            Meeting iMeet = calendar.get(i);
            for(int j=0;j<i;j++){
                Meeting jMeet = calendar.get(j);
                if(iMeet.getEndTime().compareTo(jMeet.getStartTime())>=0) continue;
                dp[i] = Math.max(dp[i],dp[j]+1);
            }
            max = Math.max(max,dp[i]);
        }

        return max;
    }
}
