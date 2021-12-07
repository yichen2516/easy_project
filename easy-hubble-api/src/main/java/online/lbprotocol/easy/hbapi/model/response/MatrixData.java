package online.lbprotocol.easy.hbapi.model.response;

import lombok.Getter;
import lombok.ToString;

/**
 * @author yichen for lfkg
 * @since 2021/11/23
 */
@Getter
@ToString
public class MatrixData {
    private String id;
    private String media_id;
    private String media_name;
    private String media_desc;
    private String media_head_img;
    private String media_content_type;
    private String media_info;
    private String media_info_type;
    private String fans;
    private String new_fans;
    private String follow;
    private String art;
    private String read;
    private String watch;
    private String share;
    private String repost;
    private String like;
    private String reply;
    private String click;
    private String play;
    private String nri; // 指数
    private String wci;
    private String save_time;
    private String update_time;
    private String rank_date;
    private String rank_position;
    private String max_read;
    private String top_art_read;
    private String avg_read;
}
