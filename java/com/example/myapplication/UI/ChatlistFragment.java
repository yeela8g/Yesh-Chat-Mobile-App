package com.example.myapplication.UI;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ContactListAdapter;
import com.example.myapplication.utilities.Info;
import com.example.myapplication.viewModels.ContactsVM;
import com.example.myapplication.viewModels.UserVM;

public class ChatlistFragment extends Fragment {
    private View _view;
    private UserVM userViewModel;
    private ContactsVM contactsViewModel;
    private RecyclerView listView;
    private ContactListAdapter adapter;
    private ImageButton button_addChat;
    private static final String THEME_PREFS_KEY = "theme_prefs";
    private static final String SELECTED_THEME_KEY = "selected_theme";
    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Retrieve the selected theme from SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(THEME_PREFS_KEY, MODE_PRIVATE);
        int selectedTheme = sharedPreferences.getInt(SELECTED_THEME_KEY, R.style.LightTheme_MyApplication);

        // Set the theme for the fragment
        requireContext().setTheme(selectedTheme);

        return inflater.inflate(R.layout.fragment_chatlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _view = view;

        userViewModel = new ViewModelProvider(requireActivity()).get(UserVM.class); //userVM role: get the user details - from localdb , using userDao
        contactsViewModel = new ViewModelProvider(requireActivity()).get(ContactsVM.class); //contactsVM role: get list of all contacts locally and then when observed syncing from service


        //present the displayName
        TextView displayName = view.findViewById(R.id.displayName);
        if (userViewModel.getUser(Info.loggedUser) == null || //if for some reason the user details are not saved in local db OR the user is missing displayName for some reason - display the username itself.
                userViewModel.getUser(Info.loggedUser).getDisplayName() == null) {
            displayName.setText(Info.loggedUser);
        } else {
            displayName.setText(userViewModel.getUser(Info.loggedUser).getDisplayName());
        }
        //present the User profilePic
        ImageView profilePic = view.findViewById(R.id.profilePic);
        if (userViewModel.getUser(Info.loggedUser) != null && userViewModel.getUser(Info.loggedUser).getProfilePic() != null) {
            String pic = userViewModel.getUser(Info.loggedUser).getProfilePic();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO && pic != "") {
                byte[] profileImage = Base64.decode(pic, Base64.DEFAULT);
                Bitmap image = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
                profilePic.setImageBitmap(image);
            }
        }

        setAdapter();
        setContactList(view);


        ImageButton logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            // Clear any user session data or perform necessary cleanup
            // For example, you can clear the user token or reset any relevant variables
            // ...
            Info.resetUserInformation();
            // Start the login activity or navigate to the login screen
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        setAddContactBtn(view);
    }

    private void setAdapter() { //create the contacts adapter and the onClick listener
        adapter = new ContactListAdapter(contactsViewModel, Info.context, contact -> { //when pressing on a contact in the recycler the chat with him will be opened
            Intent chatIntent = new Intent(Info.context, ChatActivity.class);
            Info.contactId = contact.getId();
            chatIntent.putExtra("contactUsername", contact.getUser().getUsername());
            chatIntent.putExtra("contactDisplayName", contact.getUser().getDisplayName());
            chatIntent.putExtra("contactProfilePic", contact.getUser().getProfilePic());
            startActivity(chatIntent);
        });
    }

    private void setContactList(View view) {
        listView = view.findViewById(R.id.ContactsRecyclerView); //catch the recycler element
        listView.setLayoutManager(new LinearLayoutManager(getContext())); //define a recycler manager that is linear so the items will be displayed in vertical list
        contactsViewModel.getContacts().observe(getViewLifecycleOwner(), contacts -> {
            adapter.setContactsList(contacts); //define observer on the contactsList in the viewModel and onChanges will update the new contactsList and notify the recycler to display the new data
        });
        listView.setAdapter(adapter);
        listView.setClickable(true); //Enabling clickability on the RecyclerView so it opens a chat activity with the selected contact.
    }

    private void setAddContactBtn(View view) {
        button_addChat = view.findViewById(R.id.button_addChat);
        button_addChat.setOnClickListener(v -> {

            Fragment fragment = new AddContactFragment();
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.addContactFragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            // Show the add contact fragment container
            ViewGroup addContactContainer = view.findViewById(R.id.addContactFragmentContainer);
            addContactContainer.bringToFront(); // Bring the container to the front
            addContactContainer.setVisibility(View.VISIBLE);

        });
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = getChildFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
    }
}