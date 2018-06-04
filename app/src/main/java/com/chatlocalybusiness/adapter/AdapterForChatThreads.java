package com.chatlocalybusiness.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.applozic.mobicomkit.channel.database.ChannelDatabaseService;
import com.applozic.mobicommons.commons.core.utils.DateUtils;
import com.applozic.mobicommons.people.channel.Channel;
import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.apiModel.ChatThreadListModel;
import com.chatlocalybusiness.chat.ApplozicBridge;
import com.chatlocalybusiness.fragments.ChatThreadFragment;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;

import java.util.List;

/**
 * Created by windows on 12/19/2017.
 */
public class AdapterForChatThreads extends RecyclerView.Adapter<AdapterForChatThreads.MyViewHolder> {
    Context context;
    List<ChatThreadListModel.MessageList> messageList;
      int adapterPosition = 0;
    ChatBusinessSharedPreference prefrence;
    ChatThreadFragment chatThreadFragment;
    private MessageDatabaseService messageDatabaseService;
    int messageUnReadCount = 0;
    private AlertDialog alertdialog;


    public AdapterForChatThreads(Context context, List<ChatThreadListModel.MessageList> messageList, ChatThreadFragment chatThreadFragment) {
        this.messageList = messageList;
        this.context = context;
        this.chatThreadFragment = chatThreadFragment;
        prefrence = new ChatBusinessSharedPreference(context);
        this.messageDatabaseService = new MessageDatabaseService(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_threads, parent, false);
        return new AdapterForChatThreads.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_userName.setText(messageList.get(position).getFullName());
        holder.tv_lastuserName.setText(messageList.get(position).getToFullName() + " : ");
        messageUnReadCount = 0;


        if (!messageList.get(position).getResponseBy().trim().equals("")) {
            holder.tv_liveUsers.setText(messageList.get(position).getResponseBy());
        } else holder.tv_liveUsers.setText(R.string.str_noone_response);

        if (messageList.get(position).getBusinessTags() != null)
            if (messageList.get(position).getBusinessTags().equals("1"))
                holder.iv_tagIcon.setImageResource(R.drawable.tag_blue);
            else holder.iv_tagIcon.setImageResource(R.drawable.tag_grey);


        if (messageList.get(position).getBusinessNotification().equals("1"))
            holder.iv_notifyIcon.setImageResource(R.drawable.notification_blue);
        else holder.iv_notifyIcon.setImageResource(R.drawable.notification_grey);

        if (messageList.get(position).getContentType() == 0) {
            holder.tv_lastuserName.setVisibility(View.VISIBLE);
            holder.tv_lastchat.setVisibility(View.VISIBLE);
            holder.ll_contentTypeAttachment.setVisibility(View.GONE);
            holder.tv_lastchat.setText(messageList.get(position).getMessage());
        } else if (messageList.get(position).getContentType() == 10) {
            holder.tv_lastchat.setVisibility(View.VISIBLE);
            holder.ll_contentTypeAttachment.setVisibility(View.GONE);
            holder.tv_lastuserName.setVisibility(View.GONE);
            holder.tv_lastchat.setText(messageList.get(position).getMessage());

        } else {
            holder.ll_contentTypeAttachment.setVisibility(View.VISIBLE);
            holder.tv_lastchat.setVisibility(View.GONE);

        }
//  holder.tv_unreadChatNo.setText(messageList.get(position).get);

        if (DateUtils.isSameDay(messageList.get(position).getCreatedAtTime()))
            holder.tv_lastChatTime.setText(String.valueOf(DateUtils.getFormattedDate(messageList.get(position).getCreatedAtTime())));
        else if (DateUtils.isYesterday(messageList.get(position).getCreatedAtTime()))
            holder.tv_lastChatTime.setText("yesterday");
        else
            holder.tv_lastChatTime.setText(BasicUtill.getDate(messageList.get(position).getCreatedAtTime()));

        if (messageList.get(position).getBusinessNotification().equals("0"))
            holder.iv_notifyIcon.setImageResource(R.drawable.notification_blue);
        else holder.iv_notifyIcon.setImageResource(R.drawable.notification_grey);
        if (messageList.get(position).getBusinessTags().equals("1"))
            holder.iv_tagIcon.setImageResource(R.drawable.tag_blue);
        else holder.iv_tagIcon.setImageResource(R.drawable.tag_grey);


        Glide.with(context).load(messageList.get(position).getCProfileImage()).into(holder.iv_profilePic);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                adapterPosition = position;
                return false;
            }
        });

        try {
            final Channel channel = ChannelDatabaseService.getInstance(context).getChannelByChannelKey(messageList.get(position).getGroupId());

            messageUnReadCount = messageDatabaseService.getUnreadMessageCountForChannel(channel.getKey());
        } catch (Exception ex) {
        }
        if (messageUnReadCount > 0) {
            holder.tv_unreadChatNo.setVisibility(View.VISIBLE);
            holder.tv_unreadChatNo.setText(String.valueOf(messageUnReadCount));

        } else holder.tv_unreadChatNo.setVisibility(View.INVISIBLE);


    }

    public void notifyData(List<ChatThreadListModel.MessageList> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messageList != null)
            return messageList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        RelativeLayout rl_layout;
        ImageView iv_profilePic, iv_notifyIcon, iv_tagIcon;
        TextView tv_userName, tv_lastuserName, tv_lastchat, tv_unreadChatNo, tv_lastChatTime, tv_liveUsers;
        LinearLayout ll_contentTypeAttachment;

        public MyViewHolder(final View itemView) {
            super(itemView);
            ll_contentTypeAttachment = (LinearLayout) itemView.findViewById(R.id.ll_contentTypeAttachment);
            rl_layout = (RelativeLayout) itemView.findViewById(R.id.rl_layout);
            tv_lastChatTime = (TextView) itemView.findViewById(R.id.tv_lastChatTime);
            tv_unreadChatNo = (TextView) itemView.findViewById(R.id.tv_unreadChatNo);
            tv_lastchat = (TextView) itemView.findViewById(R.id.tv_lastchat);
            tv_lastuserName = (TextView) itemView.findViewById(R.id.tv_lastuserName);
            tv_userName = (TextView) itemView.findViewById(R.id.tv_userName);
            tv_liveUsers = (TextView) itemView.findViewById(R.id.tv_liveUsers);
            iv_profilePic = (ImageView) itemView.findViewById(R.id.iv_profilePic);
            iv_notifyIcon = (ImageView) itemView.findViewById(R.id.iv_notifyIcon);
            iv_tagIcon = (ImageView) itemView.findViewById(R.id.iv_tagIcon);
            itemView.setOnCreateContextMenuListener(this);

            rl_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (messageList.get(getAdapterPosition()).getResponseByUsers() != null)
//                        if (messageList.get(getAdapterPosition()).getResponseByUsers().size() > 0)



                    if(!messageList.get(getAdapterPosition()).getIsBlocked())
                    {
                            prefrence.setChatGroupID(String.valueOf(messageList.get(getAdapterPosition()).getGroupId()), messageList.get(getAdapterPosition()).getCUserId());
                            ApplozicBridge.launchIndividualGroupChat(context, String.valueOf(messageList.get(getAdapterPosition()).getGroupId()),
                            prefrence.getUserId(), messageList.get(getAdapterPosition()).getFullName(), "0C" + messageList.get(getAdapterPosition()).getCUserId(),
                            messageList.get(getAdapterPosition()).getChatGroupId());
                    }
                    else{
                        blockThreadAlert(context,getAdapterPosition());

                    }


                }
            });
        }
        String mute = "";
        String tag = " ";

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Menu Otptions");

            if(!messageList.get(adapterPosition).getIsBlocked()) {
                if (messageList.get(adapterPosition).getBusinessNotification().equals("1"))
                    mute = "Unmute";
                else mute = "Mute";
                contextMenu.add(mute).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
//                    chatThreadFragment.notification_on(context, adapterPosition, messageList);
                        if (mute.equalsIgnoreCase("Mute"))
                            chatThreadFragment.muteGroupChat(messageList.get(adapterPosition).getGroupId(), adapterPosition, messageList);
                        else if (mute.equalsIgnoreCase("Unmute"))
                            chatThreadFragment.umuteGroupChat(messageList.get(adapterPosition).getGroupId(), adapterPosition, messageList);

                        return false;
                    }
                });
                contextMenu.add("Block").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (prefrence.getBlockThread().equalsIgnoreCase("1"))
                            chatThreadFragment.blockUserApi(context, adapterPosition, messageList, "block");
                        else
                            Toast.makeText(context, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();

                        return false;
                    }
                });
                if (messageList.get(adapterPosition).getBusinessTags().equals("0"))
                    tag = "Tag ";
                else tag = "Untag ";
                final String finalTag = tag;
                contextMenu.add(tag).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        chatThreadFragment.tagPeopleApi(context, adapterPosition, messageList, finalTag);
                        return false;
                    }
                });

            }
            else if(messageList.get(adapterPosition).getBlockSide().equalsIgnoreCase("business")) {
                contextMenu.add("Unblock").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (prefrence.getBlockThread().equalsIgnoreCase("1"))
                            chatThreadFragment.blockUserApi(context, adapterPosition, messageList, "unblock");
                        else
                            Toast.makeText(context, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();

                        return false;
                    }
                });
                if (messageList.get(adapterPosition).getBusinessTags().equals("0"))
                    tag = "Tag";
                else tag = "Untag";
                final String finalTag = tag;
                contextMenu.add(tag).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        chatThreadFragment.tagPeopleApi(context, adapterPosition, messageList, finalTag);
                        return false;
                    }
                });
            }
            else {
                if (messageList.get(adapterPosition).getBusinessTags().equals("0"))
                    tag = "Tag";
                else tag = "Untag";
                final String finalTag = tag;
                contextMenu.add(tag).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        chatThreadFragment.tagPeopleApi(context, adapterPosition, messageList, finalTag);
                        return false;
                    }
                });
            }
        /*    contextMenu.add("Remove User").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    blockedChatsActivity.blockUserApi(context,adapterPosition,messageList,"remove");
                    return false;
                }
            });
*/

           /* contextMenu.add("Delete Chat").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Toast.makeText(context, "Remove"+adapterPosition, Toast.LENGTH_SHORT).show();

                    return false;
                }
            });
*/


//            MenuInflater inflater =((Activity)context).getMenuInflater();
//            inflater.inflate(R.menu.chat_thread_menu, contextMenu);

//            PopupMenu popup = new PopupMenu(view.getContext(), view);
//            popup.getMenuInflater().inflate(R.menu.chat_thread_menu, popup.getMenu());
//            popup.setOnMenuItemClickListener(this);
//            popup.show();
        }

    }
    public void blockThreadAlert(final Context context, final int adapterPosition)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.dialog_threadblocked_alert,null);
        builder.setView(view);
        TextView blockHeading=(TextView)view.findViewById(R.id.tv_blockAlert);
        TextView tv_unblock=(TextView)view.findViewById(R.id.tv_unblock);
        if(messageList.get(adapterPosition).getBlockSide().equalsIgnoreCase("customer"))
            tv_unblock.setVisibility(View.GONE);
        else tv_unblock.setVisibility(View.VISIBLE);

        blockHeading.setText("Sorry! you can't chat, "+  messageList.get(adapterPosition).getBlockedWhom()+" has been blocked by "+messageList.get(adapterPosition).getBlockedBy());
        tv_unblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prefrence.getBlockThread().equalsIgnoreCase("1"))
                    chatThreadFragment.blockUserApi(context,adapterPosition,messageList,"unblock");
                else Toast.makeText(context, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();
                alertdialog.dismiss();

            }
        });
        alertdialog=builder.create();
        alertdialog.setCancelable(true);
        alertdialog.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertdialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialog_bg));
        }


    }


}
