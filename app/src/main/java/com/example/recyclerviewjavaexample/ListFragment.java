package com.example.recyclerviewjavaexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewjavaexample.databinding.FragmentListBinding;
import com.example.recyclerviewjavaexample.databinding.ItemGroupViewHolderBinding;
import com.example.recyclerviewjavaexample.databinding.ItemViewHolderBinding;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setAdapter(new Adapter(createItems()));
    }

    private List<VO> createItems() {
        List<VO> items = new ArrayList<>();
        for(int i = 0; i < 9999; i++) {
            if (i % 10 == 0) {
                List<VO> groupItems = new ArrayList<>();
                for (int j = 0; j < 10; j++) {
                    groupItems.add(new ItemVO(i * 10000, "Item " + (j+1)));
                }
                items.add(new GroupItemVO(i, "Group " + (i+1), groupItems));
            } else {
                items.add(new ItemVO(i, "Item " + (i+1)));
            }
        }
        return items;
    }

    private static class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public List<VO> items;
        public Adapter(List<VO> items) {
            this.items = items;
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (ViewType.ITEM == ViewType.values()[viewType]) {
                return new ViewHolder(ItemViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            } else if (ViewType.GROUP == ViewType.values()[viewType]) {
                return new GroupViewHolder(ItemGroupViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
            throw new RuntimeException();
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VO item = items.get(position);
            if(holder instanceof ViewHolder) {
                ((ViewHolder) holder).bind((ItemVO)item);
            } else if(holder instanceof GroupViewHolder) {
                ((GroupViewHolder) holder).bind((GroupItemVO)item);
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position) {
            VO item = items.get(position);
            if (item instanceof ItemVO) {
                return ViewType.ITEM.ordinal();
            }
            else if (item instanceof GroupItemVO) {
                return ViewType.GROUP.ordinal();
            }
            throw new RuntimeException();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemViewHolderBinding binding;
        public ViewHolder(ItemViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ItemVO itemVO) {
            binding.text.setText(itemVO.getText());
        }
    }

    private static class GroupViewHolder extends RecyclerView.ViewHolder {
        private final ItemGroupViewHolderBinding binding;
        private final Adapter adapter = new Adapter(new ArrayList<>());

        public GroupViewHolder(ItemGroupViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.recyclerView.setAdapter(adapter);
        }

        public void bind(GroupItemVO itemVO) {
            adapter.items = itemVO.getItems();
            adapter.notifyDataSetChanged();
            binding.title.setText(itemVO.getText());
        }
    }
}
