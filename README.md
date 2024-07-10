# PhoneNumberMaskApp
輸入完會自動遮蔽手機號碼部分內容，且能夠手動開關遮蔽內容的App Demo

**做這個demo的原因**
因為工作上遇到因為資安問題需要遮蔽手機號碼的需求，根據要求做了一個自己比較喜歡的版本。

**功能解釋**
輸入手機號碼時會打開眼睛不遮蔽內容，輸入完後會關閉眼睛並自動遮蔽4-6碼的內容，在非編輯的狀態下可以透過旁邊的眼睛按鈕來切換是否遮蔽內容。
由於有不同的需求，所以多寫了一個boolean判斷，當點擊這個EditText時要不要清空它裡面的內容。

**程式解釋**
utils裡面有一個PhoneNumberMaskTextWatcher 在你需要使用的EditText的頁面中宣告，並按照PhoneNumberMaskTextWatcher中的註解教學來完成即可。
