package edu.uncc.assessment04.fragments.giftlists;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assessment04.R;
import edu.uncc.assessment04.databinding.FragmentAddItemToGiftlistBinding;
import edu.uncc.assessment04.models.GiftList;
import edu.uncc.assessment04.models.User;

public class AddItemToGiftListFragment extends Fragment {
    private static final String ARG_PARAM_GIFT_LIST = "ARG_PARAM_GIFT_LIST";
    private GiftList mGiftlist;
    private User mCurrentUser;

    public AddItemToGiftListFragment() {
        // Required empty public constructor
    }

    public static AddItemToGiftListFragment newInstance(GiftList giftList) {
        AddItemToGiftListFragment fragment = new AddItemToGiftListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_GIFT_LIST, giftList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGiftlist = (GiftList) getArguments().getSerializable(ARG_PARAM_GIFT_LIST);
        }
    }

    FragmentAddItemToGiftlistBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddItemToGiftlistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Item to List");
        mCurrentUser = mListener.getCurrentUser();

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelAddItemToGiftList(mGiftlist);
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = binding.editTextName.getText().toString().trim();
                String quantityStr = binding.editTextQuantity.getText().toString().trim();
                if (itemName.isEmpty()) {
                    Toast.makeText(getContext(), "Item name cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    try{
                        Integer quantity = Integer.parseInt(quantityStr);
                        mListener.onAddItemToGiftList(mCurrentUser, mGiftlist, itemName, quantity);

                    } catch (NumberFormatException e){
                        Toast.makeText(getContext(), "Quantity must be a number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }

    AddItemToGiftListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddItemToGiftListListener) {
            mListener = (AddItemToGiftListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddItemToGiftListListener");
        }
    }

    public interface AddItemToGiftListListener {
        User getCurrentUser();
        void onAddItemToGiftList(User user, GiftList giftList, String itemName, Integer quantity);
        void onCancelAddItemToGiftList(GiftList giftList);
    }
}