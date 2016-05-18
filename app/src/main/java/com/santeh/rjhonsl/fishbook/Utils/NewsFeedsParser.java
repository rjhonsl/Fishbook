package com.santeh.rjhonsl.fishbook.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhonsl on 5/17/2016.
 */
public class NewsFeedsParser {

    public static List<VarFishbook> parseFeed(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<VarFishbook> feedlist = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                VarFishbook fbOjb = new VarFishbook();


                /**
                 * CONDITIONS FOR COMMENTS
                 * */
                if (obj.has("feedcomments_id")){
                    fbOjb.setComment_Id(obj.getString("feedcomments_id"));
                }

                if (obj.has("feedcomments_mainid")){
                    fbOjb.setComment_MainId(obj.getString("feedcomments_mainid"));
                }

                if (obj.has("feedcomments_uid")){
                    fbOjb.setComment_UID(obj.getString("feedcomments_uid"));
                }

                if (obj.has("feedcomments_content")){
                    fbOjb.setComment_content(obj.getString("feedcomments_content"));
                }

                if (obj.has("feedcomments_datecommented")){
                    fbOjb.setComment_dateCommented(obj.getString("feedcomments_datecommented"));
                }

                if (obj.has("feedcomments_loclat")){
                    fbOjb.setComment_loclat(obj.getString("feedcomments_loclat"));
                }

                if (obj.has("feedcomments_loclong")){
                    fbOjb.setComment_loclong(obj.getString("feedcomments_loclong"));
                }

                if (obj.has("feedcomments_fetchat")){
                    fbOjb.setComment_fetchat(obj.getString("feedcomments_fetchat"));
                }



                /**
                 * CONDITIONS FOR COMMENTS
                 * */
                if (obj.has("feedcontents_id")){
                    fbOjb.setContent_id(obj.getString("feedcontents_id"));
                }
                if (obj.has("feedcontents_mainid")){
                    fbOjb.setContent_mainid(obj.getString("feedcontents_mainid"));
                }
                if (obj.has("feedcontents_type")){
                    fbOjb.setContent_type(obj.getString("feedcontents_type"));
                }
                if (obj.has("feedcontents_description")){
                    fbOjb.setContent_description(obj.getString("feedcontents_description"));
                }
                if (obj.has("feedcontents_imageurl")){
                    fbOjb.setContent_imageUrl(obj.getString("feedcontents_imageurl"));
                }
                if (obj.has("feedcontents_event")){
                    fbOjb.setContent_event(obj.getString("feedcontents_event"));
                }
                if (obj.has("feedcontents_fileurl")){
                    fbOjb.setContent_fileURL(obj.getString("feedcontents_fileurl"));
                }
                if (obj.has("feedcontents_fetchat")){
                    fbOjb.setContent_fetchat(obj.getString("feedcontents_fetchat"));
                }



                /**
                 * CONDITIONS FOR SUBCOMMENTS
                 * */
                if (obj.has("feedsubcomments_id")){
                    fbOjb.setSubcomm_id(obj.getString("feedsubcomments_id"));
                }
                if (obj.has("feedsubcomments_commentid")){
                    fbOjb.setSubcomm_commentId(obj.getString("feedsubcomments_commentid"));
                }
                if (obj.has("feedsubcomments_uid")){
                    fbOjb.setSubcomm_uid(obj.getString("feedsubcomments_uid"));
                }
                if (obj.has("feedsubcomments_content")){
                    fbOjb.setSubcomm_content(obj.getString("feedsubcomments_content"));
                }
                if (obj.has("feedsubcomments_datecommented")){
                    fbOjb.setSubcomm_dateCommented(obj.getString("feedsubcomments_datecommented"));
                }
                if (obj.has("feedsubcomments_loclat")){
                    fbOjb.setSubcomm_loclat(obj.getString("feedsubcomments_loclat"));
                }
                if (obj.has("feedsubcomments_loclong")){
                    fbOjb.setSubcomm_loclong(obj.getString("feedsubcomments_loclong"));
                }
                if (obj.has("feedsubcomments_fetchat")){
                    fbOjb.setSubcomm_fetchat(obj.getString("feedsubcomments_fetchat"));
                }


                /**
                 * CONDITIONS FOR MAIN
                 * */
                if (obj.has("feed_main_id")){
                    fbOjb.setMain_id(obj.getString("feed_main_id"));
                }
                if (obj.has("feed_main_uid")){
                    fbOjb.setMain_mainuid(obj.getString("feed_main_uid"));
                }
                if (obj.has("feed_main_date")){
                    fbOjb.setMain_date(obj.getString("feed_main_date"));
                }
                if (obj.has("feed_main_loclat")){
                    fbOjb.setMain_loclat(obj.getString("feed_main_loclat"));
                }
                if (obj.has("feed_main_loclong")){
                    fbOjb.setMain_loclong(obj.getString("feed_main_loclong"));
                }
                if (obj.has("feed_main_fetch_at")){
                    fbOjb.setMain_fetchat(obj.getString("feed_main_fetch_at"));
                }
                if (obj.has("feed_main_seen_state")){
                    fbOjb.setMain_seenState(obj.getString("feed_main_seen_state"));
                }


                feedlist.add(fbOjb);

            }

            return feedlist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
