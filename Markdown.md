# Xây Dựng Hệ Thống Tuyển Dụng

## 1. Tạo các Entity để tạo ra các bảng trong cơ sở dữ liệu

## 2. Viết các Repositories Interface

## 3. Viết các lớp Services

## 4. Tạo các trang web cho phép công ty đăng tin tuyển dụng
### 4.1. Trang chủ: chứa 2 button 'Công ty' và 'Ứng viên' để phân quyền
#### Hình ảnh minh họa:

![Create Job](images/Home.png)
### 4.2. Ấn vào công ty sẽ hiển thị form nhập id công ty để đăng nhập
![Create Job](images/company-id-form.png)
### 4.3. Hiên list công viec theo id công ty
![Create Job](images/job-list.png)
### 4.4. Thêm công việc
![Create Job](images/create.png)
### 4.5. Xem chi tiết công việc
![Create Job](images/detail-job.png)
### 4.6 Hiện thị danh sách ứng viên phân trang
![Create Job](images/candidate-paging.png)
### 4.7 Hiện thị danh sách ứng viên không phân trang
![Create Job](images/candidate-nonpaging.png)

### 4.8 Ấn vào list ứng viên phân trang có thêm chức năng tìm kiếm công việc nâng cao và tìm kiếm ứng viên nâng cao (dựa trên nhiều tiêu chí)
### Ấn vào tìm kiếm công việc nâng cao
![Create Job](images/find-job-advance.png)
![Create Job](images/result-job-search.png)

### Ấn vào tìm kiếm ứng viên nâng cao
![Create Job](images/find-candidate-advance.png)
![Create Job](images/result-candidate-search.png)




## 5. Gợi ý công việc cho ứng viên dựa trên kỹ năng

Khi ứng viên đăng nhập, hệ thống sẽ tự động gợi ý các công việc phù hợp với các kỹ năng mà ứng viên đã có. Hệ thống sử dụng thông tin từ ứng viên và công việc để đưa ra các gợi ý chính xác.

### Hình ảnh minh họa:

![Job Suggestions](images/Candidate-log.png)
Khi bấm vào 'Gợi ý công việc'
![Job Suggestions](images/suggest-job.png)

## 6. Giúp các công ty tìm ứng viên có kỹ năng phù hợp

Hệ thống cũng hỗ trợ công ty tìm kiếm ứng viên có kỹ năng phù hợp. Công ty có thể tìm kiếm ứng viên dựa trên các kỹ năng yêu cầu và gửi lời mời tham gia công việc.
![Create Job](images/detail-job-candidate.png)

## 7. Đề xuất kỹ năng mà ứng viên chưa có để học

Cuối cùng, hệ thống đề xuất các kỹ năng mà ứng viên chưa có để học. Điều này giúp ứng viên nâng cao năng lực và cải thiện cơ hội tìm việc.

### Hình ảnh minh họa:


![Skill Suggestions](images/Candidate-log.png)
Khi bấm vào 'Các kỹ năng cần học'
![Skill Suggestions](images/skill-suggest.png)
