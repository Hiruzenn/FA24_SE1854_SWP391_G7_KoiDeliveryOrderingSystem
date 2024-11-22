package com.swp391.group7.KoiDeliveryOrderingSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_REQUEST(1000, "Yêu cầu không hợp lệ"),
    ACCOUNT_REGISTERED(1001, "Người dùng đã được đăng ký"),
    INVALID_PASSWORD(1002, "Mật khẩu không hợp lệ"),
    USER_NOT_EXISTED(1003, "Người dùng không tồn tại"),
    INVALID_REPEAT_PASSWORD(1004, "Mật khẩu xác nhận không hợp lệ"),
    UNAUTHENTICATED(1005, "Chưa xác thực"),
    NOT_LOGIN(1006, "Chưa đăng nhập"),
    INVALID_TOKEN(1007, "Mã token không hợp lệ"),
    UNVERIFIED_ACCOUNT(1008, "Tài khoản chưa được xác minh"),
    ORDER_NOT_FOUND(1010, "Không tìm thấy đơn hàng"),
    FISH_PROFILE_NOT_FOUND(1012, "Không tìm thấy thông tin con cá"),
    CERTIFICATE_NOT_FOUND(1013, "Không tìm thấy chứng chỉ"),
    CERTIFICATE_EXISTED(1014, "Chứng chỉ đã tồn tại"),
    HEALTH_SERVICE_CATEGORY_NOT_FOUND(1015, "Không tìm thấy danh loại dịch vụ sức khỏe"),
    HEALTH_SERVICE_ORDER_NOT_FOUND(1016, "Không tìm thấy đơn đặt dịch vụ thêm"),
    HEALTH_SERVICE_ORDER_IS_EXISTED(1017, "Đơn đặt dịch vụ thêm đã tồn tại"),
    NOT_ENOUGH_CHECKING_KOI_HEALTH(1018, "Chưa đủ kiểm tra sức khỏe cá trong đơn hàng"),
    CHECKING_KOI_HEALTH_NOT_FOUND(1019, "Không tìm thấy kiểm tra sức khỏe của cá"),
    USER_NOT_ADMIN(1020, "Người dùng không phải là quản trị viên"),
    USER_NOT_CUSTOMER(1021, "Người dùng không phải là khách hàng"),
    DELIVERY_METHOD_NOT_FOUND(1022, "Không tìm thấy phương thức giao hàng"),
    NEED_DELIVERY_HISTORY(1023, "Cần lịch sử giao hàng trước khi hoàn tất biên bản giao hàng"),
    PACKAGE_NOT_FOUND(1024, "Không tìm thấy gói hàng"),
    PACK_ORDER_BEFORE(1025, "Trạng thái gói hàng phải là ĐÃ ĐÓNG GÓI trước khi hoàn tất đơn hàng"),
    REQUIRED_HANDOVER_DOCUMENT(1026, "Cần biên bản bàn giao trước khi khi tạo gói hàng"),
    REPORT_NOT_FOUND(1027, "Không tìm thấy báo cáo"),
    DELETE_HANDOVER_PENDING(1028, "Chỉ có thể xóa biên bản giao hàng với trạng thái chờ xử lý"),
    HANDOVER_DOCUMENT_NOT_FOUND(1029, "Không tìm thấy biên bản giao hàng"),
    CUSTOM_DECLARATION_NOT_EXISTED(1030, "Không tìm thấy khai báo hải quan"),
    HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND(1032, "Không tìm thấy lịch sử giao hàng"),
    FISH_CATEGORY_NOT_FOUND(1033, "Không tìm thấy loại cá"),
    ROLE_NOT_EXISTED(1034, "Không tìm thấy Role"),
    INTERNAL_SERVER_ERROR(1035, "Lỗi máy chủ nội bộ"),
    FEEDBACK_ORDER_COMPLETED(1036, "Chỉ có thể phản hồi cho đơn hàng đã hoàn thành"),
    NOT_DELETE_PACKED(1037, "Không thể xóa gói hàng có trạng thái đã đóng gói"),
    PACKAGE_FISH_HEALTHY(1038, "Chỉ có thể tạo gói hàng khi tất cả cá đều khỏe mạnh"),
    DELIVERY_METHOD_IN_USE(1039, "Phương thức giao hàng đang được sử dụng"),
    HEALTH_SERVICE_CATEGORY_IN_USE(1040, "Loại dịch vụ thêm đang được sử dụng"),
    INVALID_OLD_PASSWORD(1041, "Mật khẩu cũ không hợp lệ"),
    DUPLICATE_PASSWORD(1042, "Mật khẩu mới phải khác mật khẩu cũ"),
    INVALID_PASSWORD_FORMAT(1043, "Mật khẩu phải có ít nhất 6 ký tự, bao gồm ít nhất một chữ cái và một số"),
    HANDOVER_EXISTED(1044, "Biên bản bàn giao đã tồn tại"),
    PACKAGE_EXISTED(1045, "Gói hàng đã tồn tại"),
    FISH_CATEGORY_IN_USE(1046,"Loại cá đang được sử dụng")
    ;

    private final int code;
    private final String message;
}