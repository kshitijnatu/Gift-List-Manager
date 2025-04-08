package edu.uncc.assessment04.fragments.giftlists;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.uncc.assessment04.R;
import edu.uncc.assessment04.databinding.FragmentListDetailsBinding;
import edu.uncc.assessment04.databinding.ListItemGiftListItemBinding;
import edu.uncc.assessment04.models.GiftList;
import edu.uncc.assessment04.models.GiftListItem;
import edu.uncc.assessment04.models.User;

public class GiftListDetailsFragment extends Fragment {
    private static final String ARG_PARAM_GIFT_LIST= "ARG_PARAM_GIFT_LIST";
    FragmentListDetailsBinding binding;
    User mCurrentUser;
    private GiftList mGiftList;

    public static GiftListDetailsFragment newInstance(GiftList giftList) {
        GiftListDetailsFragment fragment = new GiftListDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_GIFT_LIST, giftList);
        fragment.setArguments(args);
        return fragment;
    }


    public GiftListDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGiftList = (GiftList) getArguments().getSerializable(ARG_PARAM_GIFT_LIST);
        }
    }

    ArrayList<GiftListItem> mGiftListItems = new ArrayList<>();
    GiftListItemAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Gift Lists");

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.gift_list_details_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.add_new_list_item_action){
                    mListener.gotoAddGiftListItem(mGiftList);
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        mCurrentUser = mListener.getCurrentUser();
        mGiftListItems.clear();
        mGiftListItems.addAll(mListener.getAllItemsForGiftList(mGiftList));

        adapter = new GiftListItemAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBackToGiftLists();
            }
        });

        adapter.notifyDataSetChanged();
    }

    class GiftListItemAdapter extends RecyclerView.Adapter<GiftListItemAdapter.GiftListItemViewHolder>{

        @NonNull
        @Override
        public GiftListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemGiftListItemBinding itemBinding = ListItemGiftListItemBinding.inflate(getLayoutInflater(), parent, false);
            return new GiftListItemViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GiftListItemViewHolder holder, int position) {
            GiftListItem listItem = mGiftListItems.get(position);
            holder.bind(listItem);
        }

        @Override
        public int getItemCount() {
            return mGiftListItems.size();
        }

        class GiftListItemViewHolder extends RecyclerView.ViewHolder{
            ListItemGiftListItemBinding itemBinding;
            GiftListItem mGiftListItem;
            public GiftListItemViewHolder(ListItemGiftListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(GiftListItem giftListItem) {
                this.mGiftListItem = giftListItem;

                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.deleteGiftListItem(mGiftList, mGiftListItem);
                        mGiftListItems.clear();
                        mGiftListItems.addAll(mListener.getAllItemsForGiftList(mGiftList));
                        notifyDataSetChanged();
                    }
                });

                itemBinding.textViewName.setText(giftListItem.getName());
                itemBinding.textViewPriority.setText(String.valueOf(giftListItem.getQuantity()) + " items");
            }
        }
    }

    GiftListDetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GiftListDetailsListener) {
            mListener = (GiftListDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement GiftListDetailsListener");
        }
    }

    public interface GiftListDetailsListener {
        User getCurrentUser();
        void gotoAddGiftListItem(GiftList giftList);
        ArrayList<GiftListItem> getAllItemsForGiftList(GiftList giftList);
        void goBackToGiftLists();
        void deleteGiftListItem(GiftList giftList, GiftListItem giftListItem);
    }
}