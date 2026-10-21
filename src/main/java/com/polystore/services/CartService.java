package com.polystore.services;

import com.polystore.models.Item;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SessionScope
@Service
public class CartService {
    
    // Dùng HashMap để quản lý giỏ hàng, Key là ID sản phẩm, Value là chi tiết Item
    private Map<Integer, Item> map = new HashMap<>();

    // Thêm sản phẩm vào giỏ
    public void add(Item item) {
        Item existedItem = map.get(item.getId());
        if (existedItem != null) {
            existedItem.setQty(existedItem.getQty() + 1); // Tăng số lượng nếu đã có
        } else {
            map.put(item.getId(), item); // Thêm mới nếu chưa có
        }
    }

    // Xóa sản phẩm khỏi giỏ
    public void remove(Integer id) {
        map.remove(id);
    }

    // Cập nhật số lượng
    public void update(Integer id, int qty) {
        Item item = map.get(id);
        if (item != null) {
            item.setQty(qty);
        }
    }

    // Xóa sạch giỏ hàng
    public void clear() {
        map.clear();
    }

    // Lấy toàn bộ danh sách sản phẩm trong giỏ
    public Collection<Item> getItems() {
        return map.values();
    }

    // Đếm tổng số lượng mặt hàng
    public int getCount() {
        return map.values().stream().mapToInt(Item::getQty).sum();
    }

    // Tính tổng thành tiền
    public double getAmount() {
        return map.values().stream().mapToDouble(item -> item.getPrice() * item.getQty()).sum();
    }
}