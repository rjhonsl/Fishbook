package com.santeh.rjhonsl.fishbook.Utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhonsl on 5/17/2016.
 */
public class NewsFeedsParser {

    public static List<VarFishBook> parseFeed(String content, Context context) {

        try {
            JSONArray ar = new JSONArray(content);
            List<VarFishBook> feedlist = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                VarFishBook fbObj = new VarFishBook();


                /**
                 * CONDITIONS FOR COMMENTS
                 * */
                if (obj.has("feedcomments_id")){
                    fbObj.setComment_Id(obj.getString("feedcomments_id"));
                }

                if (obj.has("feedcomments_mainid")){
                    fbObj.setComment_MainId(obj.getString("feedcomments_mainid"));
                }

                if (obj.has("feedcomments_uid")){
                    fbObj.setComment_UID(obj.getString("feedcomments_uid"));
                }

                if (obj.has("feedcomments_content")){
                    fbObj.setComment_content(obj.getString("feedcomments_content"));
                }

                if (obj.has("feedcomments_datecommented")){
                    fbObj.setComment_dateCommented(obj.getString("feedcomments_datecommented"));
                }

                if (obj.has("feedcomments_loclat")){
                    fbObj.setComment_loclat(obj.getString("feedcomments_loclat"));
                }

                if (obj.has("feedcomments_loclong")){
                    fbObj.setComment_loclong(obj.getString("feedcomments_loclong"));
                }

                if (obj.has("feedcomments_fetchat")){
                    fbObj.setComment_fetchat(obj.getString("feedcomments_fetchat"));
                }



                /**
                 * CONDITIONS FOR CONTENTS
                 * */
                if (obj.has("feedcontents_id")){
                    fbObj.setContent_id(obj.getString("feedcontents_id"));
                }
                if (obj.has("feedcontents_mainid")){
                    fbObj.setContent_mainid(obj.getString("feedcontents_mainid"));
                }
                if (obj.has("feedcontents_type")){
                    fbObj.setContent_type(obj.getString("feedcontents_type"));
                }
                if (obj.has("feedcontents_description")){
                    fbObj.setContent_description(obj.getString("feedcontents_description"));
                }
                if (obj.has("feedcontents_imageurl")){
                    fbObj.setContent_imageUrl(obj.getString("feedcontents_imageurl"));
                }
                if (obj.has("feedcontents_event")){
                    fbObj.setContent_event(obj.getString("feedcontents_event"));
                }
                if (obj.has("feedcontents_fileurl")){
                    fbObj.setContent_fileURL(obj.getString("feedcontents_fileurl"));
                }
                if (obj.has("feedcontents_fetchat")){
                    fbObj.setContent_fetchat(obj.getString("feedcontents_fetchat"));
                }



                /**
                 * CONDITIONS FOR SUBCOMMENTS
                 * */
                if (obj.has("feedsubcomments_id")){
                    fbObj.setSubcomm_id(obj.getString("feedsubcomments_id"));
                }
                if (obj.has("feedsubcomments_commentid")){
                    fbObj.setSubcomm_commentId(obj.getString("feedsubcomments_commentid"));
                }
                if (obj.has("feedsubcomments_uid")){
                    fbObj.setSubcomm_uid(obj.getString("feedsubcomments_uid"));
                }
                if (obj.has("feedsubcomments_content")){
                    fbObj.setSubcomm_content(obj.getString("feedsubcomments_content"));
                }
                if (obj.has("feedsubcomments_datecommented")){
                    fbObj.setSubcomm_dateCommented(obj.getString("feedsubcomments_datecommented"));
                }
                if (obj.has("feedsubcomments_loclat")){
                    fbObj.setSubcomm_loclat(obj.getString("feedsubcomments_loclat"));
                }
                if (obj.has("feedsubcomments_loclong")){
                    fbObj.setSubcomm_loclong(obj.getString("feedsubcomments_loclong"));
                }
                if (obj.has("feedsubcomments_fetchat")){
                    fbObj.setSubcomm_fetchat(obj.getString("feedsubcomments_fetchat"));
                }


                /**
                 * CONDITIONS FOR MAIN
                 * */
                if (obj.has("feed_main_id")){
                    fbObj.setMain_id(obj.getString("feed_main_id"));
                }
                if (obj.has("feed_main_uid")){
                    fbObj.setMain_mainuid(obj.getString("feed_main_uid"));
                }
                if (obj.has("feed_main_date")){
                    fbObj.setMain_date(obj.getString("feed_main_date"));
                }
                if (obj.has("feed_main_loclat")){
                    fbObj.setMain_loclat(obj.getString("feed_main_loclat"));
                }
                if (obj.has("feed_main_loclong")){
                    fbObj.setMain_loclong(obj.getString("feed_main_loclong"));
                }
                if (obj.has("feed_main_fetch_at")){
                    fbObj.setMain_fetchat(obj.getString("feed_main_fetch_at"));
                }
                if (obj.has("feed_main_seen_state")){
                    fbObj.setMain_seenState(obj.getString("feed_main_seen_state"));
                }



                /**
                 * FOR USER DETAILS
                * */

                if (obj.has("users_id")){
                    fbObj.setCurrentUserID(obj.getString("users_id"));
                }

                if (obj.has("assigned_area")){
                    fbObj.setAssignedArea(obj.getString("assigned_area"));
                }

                if (obj.has("users_userlvl")){
                    fbObj.setCurrentuserLvl(obj.getInt("users_userlvl"));
                }

                if (obj.has("users_firstname")){
                    fbObj.setCurrentUserFirstname(obj.getString("users_firstname"));
                }

                if (obj.has("users_lastname")){
                    fbObj.setCurrentUserLastname(obj.getString("users_lastname"));
                }

                if (obj.has("users_username")){
                    fbObj.setCurrentUserName(obj.getString("users_username"));
                }


                if (obj.has("dateAdded")){
                    fbObj.setDateAddedToDB(obj.getString("dateAdded"));
                }

                if (obj.has("isactive")){
                    if(!obj.isNull("isactive")){
                        fbObj.setIsactive(obj.getInt("isactive"));
                    }
                }

                if (obj.has("users_device_id")){
                    if(!obj.isNull("users_device_id")){
                        fbObj.setDeviceID(obj.getString("users_device_id"));
                    }
                }



                if (obj.has("aqua")) {
                    fbObj.setIsAquaActive(obj.getInt("aqua"));
                }else{
                    fbObj.setIsAquaActive(0);
                }

                if (obj.has("petone")) {
                    fbObj.setIsPetOneActive(obj.getInt("petone"));
                }else {
                    fbObj.setIsPetOneActive(0);
                }

                if (obj.has("hogs")) {
                    fbObj.setIsHogsActive(obj.getInt("hogs"));
                }else {
                    fbObj.setIsHogsActive(0);
                }

                if (obj.has("commentcount")) {
                    fbObj.setCommentCount(obj.getInt("commentcount"));
                }else {
                    fbObj.setCommentCount(0);
                }



//                fbObj.setMain_Address(Helper.LocationUtil.getAddress(context, fbObj.getMain_loclat(), fbObj.getMain_loclong()));

                feedlist.add(fbObj);

            }

            return feedlist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
