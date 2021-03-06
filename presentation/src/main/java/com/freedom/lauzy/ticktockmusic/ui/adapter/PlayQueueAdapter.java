package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.lauzy.freedom.librarys.widght.fonts.SubTextUtil;

import java.util.List;

/**
 * Desc : 播放队列Adapter
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayQueueAdapter extends BaseQuickAdapter<SongEntity, BaseViewHolder> {

    public PlayQueueAdapter(@LayoutRes int layoutResId, @Nullable List<SongEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongEntity item) {
//        if (MusicManager.getInstance().getCurPosition() == helper.getAdapterPosition()) {
        if (MusicManager.getInstance().getCurrentSong().equals(item)) {
            ColorStateList csl = ThemeUtils.getThemeColorStateList(mContext, R.color.color_tab);
            helper.setTextColor(R.id.txt_queue_title, csl.getDefaultColor());
            helper.setTextColor(R.id.txt_queue_singer, csl.getDefaultColor());
        } else {
            helper.setTextColor(R.id.txt_queue_title, ContextCompat.getColor(mContext, R.color.txt_black));
            helper.setTextColor(R.id.txt_queue_singer, ContextCompat.getColor(mContext, R.color.gray_dark));
        }
        helper.setText(R.id.txt_queue_title, SubTextUtil.addEllipsis(item.title, 15))
                .setText(R.id.txt_queue_singer, item.artistName);
        helper.getView(R.id.img_queue_delete).setOnClickListener(deleteItemListener(helper, item));
        helper.getView(R.id.layout_queue_item).setOnClickListener(playListener(helper));
    }

    private View.OnClickListener deleteItemListener(BaseViewHolder helper, SongEntity item) {
        return v -> {
            if (mQueueItemListener != null)
                mQueueItemListener.deleteQueueItem(helper.getAdapterPosition(), item);
        };
    }

    private View.OnClickListener playListener(BaseViewHolder helper) {
        return v -> new Handler().postDelayed(() -> {
//            notifyItemChanged(MusicManager.getInstance().getCurPosition());
//            MusicManager.getInstance().playLocalQueue(mData, MusicUtil.getSongIds(mData), helper.getAdapterPosition());
//            notifyItemChanged(helper.getAdapterPosition());
            if (mQueueItemListener!= null) {
                mQueueItemListener.playNewSong(helper.getAdapterPosition());
            }
        }, 100);
    }

    private QueueItemListener mQueueItemListener;

    public void setDeleteQueueItemListener(QueueItemListener queueItemListener) {
        mQueueItemListener = queueItemListener;
    }

    public interface QueueItemListener {
        void playNewSong(int position);
        void deleteQueueItem(int position, SongEntity entity);
    }
}
