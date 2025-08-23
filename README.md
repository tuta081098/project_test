Để chạy project thực hiện các nội dung sau
	Trong thư mục projectTestPVCB chạy các câu lệnh sau để thực hiện build
		- mvn clean package -DskipTests
		- docker build -t project-pvcb .
		- docker run -d -p 8080:8080 project-pvcb
Các bước test API
	Import file API.postman_collection.json
		Thứ tự test API 
			- B1: Login API (http://localhost:8080/auth/login?)
				username=admin (user admin) có quyền upload file
				username=user1 (user người chơi) có quyền gọi api spin và claim
			- B2: upload file API (http://localhost:8080/admin/cards/upload )
				Chọn file có sẵn trong project (file_test.csv) hoặc sửa lại theo mẫu có sẵn
			- B3: Quay thường (http://localhost:8080/user/spin)
				Người chơi quay thưởng trường hợp trúng thưởng hệ thống lưu số lần và trả thẻ cào điện thoại trong API claim
			- B4 Nhận thưởng (http://localhost:8080/user/claim)
				Người chơi nhận mã thẻ cào theo số lần đã trúng thưởng