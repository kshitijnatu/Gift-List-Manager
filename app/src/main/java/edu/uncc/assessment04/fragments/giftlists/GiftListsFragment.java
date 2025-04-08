package edu.uncc.assessment04.fragments.giftlists;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.assessment04.R;
import edu.uncc.assessment04.databinding.FragmentGiftListsBinding;
import edu.uncc.assessment04.databinding.ListItemGiftListBinding;
import edu.uncc.assessment04.models.GiftList;
import edu.uncc.assessment04.models.User;

public class GiftListsFragment extends Fragment {
    public GiftListsFragment() {
        // Required empty public constructor
    }

    FragmentGiftListsBinding binding;
    User mCurrentUser;
    ArrayList<GiftList> mGiftLists = new ArrayList<>();
    GiftListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGiftListsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Gift Lists");

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.gift_lists_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.add_new_gift_list_action){
                    mListener.gotoAddNewGiftList();
                    return true;
                } else if(menuItem.getItemId() == R.id.logout_action){
                    mListener.logout();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
        mCurrentUser = mListener.getCurrentUser();
        mGiftLists.clear();
        mGiftLists.addAll(mListener.getAllGiftListsForUser(mCurrentUser));

        adapter = new GiftListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    class GiftListAdapter extends RecyclerView.Adapter<GiftListAdapter.GiftListViewHolder>{

        @NonNull
        @Override
        public GiftListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemGiftListBinding itemBinding = ListItemGiftListBinding.inflate(getLayoutInflater(), parent, false);
            return new GiftListViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GiftListViewHolder holder, int position) {
            GiftList giftList = mGiftLists.get(position);
            holder.bind(giftList);
        }

        @Override
        public int getItemCount() {
            return mGiftLists.size();
        }

        class GiftListViewHolder extends RecyclerView.ViewHolder{
            ListItemGiftListBinding itemBinding;
            GiftList mGiftList;
            public GiftListViewHolder(ListItemGiftListBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(GiftList giftList) {
                this.mGiftList = giftList;
                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoListDetails(mGiftList);
                    }
                });

                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.deleteGiftList(mGiftList);
                        mGiftLists.clear();
                        mGiftLists.addAll(mListener.getAllGiftListsForUser(mCurrentUser));
                        notifyDataSetChanged();
                    }
                });

                itemBinding.textViewName.setText(mGiftList.getName());
            }
        }
    }

    GiftListsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GiftListsListener) {
            mListener = (GiftListsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement GiftListsListener");
        }
    }

    public interface GiftListsListener {
        User getCurrentUser();
        void gotoAddNewGiftList();
        void logout();
        void gotoListDetails(GiftList toDoList);
        ArrayList<GiftList> getAllGiftListsForUser(User user);
        void deleteGiftList(GiftList toDoList);
    }
}