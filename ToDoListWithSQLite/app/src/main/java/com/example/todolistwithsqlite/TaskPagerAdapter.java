package com.example.todolistwithsqlite;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TaskPagerAdapter extends FragmentStateAdapter {
    
    public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CurrentTasksFragment();
            case 1:
                return new CompletedTasksFragment();
            default:
                return new CurrentTasksFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
} 