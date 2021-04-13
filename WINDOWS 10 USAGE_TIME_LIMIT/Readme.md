# Windows 10 Usage Time Limit 

This application contains powershell script files which are also converted to .exe to function well in windows environment. <br>
It is for local windows accounts usage limitation only.<br>
# How it works
The app will get triggered when os boots and will run required scripts before login and after login in local account specified in scripts.<br>
After Running It Will Show Messages Like--
![1](https://user-images.githubusercontent.com/71058061/114491930-23a2be80-9c35-11eb-98ba-1a9a7b31146a.PNG)
![2](https://user-images.githubusercontent.com/71058061/114491936-24d3eb80-9c35-11eb-9550-a2fbaaeea3d0.PNG)
![3](https://user-images.githubusercontent.com/71058061/114491939-256c8200-9c35-11eb-89bd-a250a87bc367.PNG)
![4](https://user-images.githubusercontent.com/71058061/114491941-269daf00-9c35-11eb-8371-4755091bc92a.PNG) <br>
After 90 min Timeout OS will restart and change current local acccounts password to something else which is specified in script.<br>
When OS boots next day it will reset current local acccounts password to old password which is also specified in script.

# Note - It will only stop if checkpost.exe if terminated manually , which can be prevented by renaming .exe file (as Task Manager shows file names as process names).<br>

# Setup --<br>
Extract Project Files in below dir--<br>
![location](https://user-images.githubusercontent.com/71058061/114490978-6cf20e80-9c33-11eb-87d8-306dbbfd5ff9.PNG)
One needs to be in admin account (as it needs admin rights to function) and from there schedule two tasks like ---<br>
![1](https://user-images.githubusercontent.com/71058061/114490954-6499d380-9c33-11eb-8edf-163b94ca25ec.PNG)
![2-1](https://user-images.githubusercontent.com/71058061/114490959-66639700-9c33-11eb-82ff-a1e1d72d85a1.PNG)
![2-2](https://user-images.githubusercontent.com/71058061/114490961-66fc2d80-9c33-11eb-929e-ac9e78093ec4.PNG)
![2-3](https://user-images.githubusercontent.com/71058061/114490964-6794c400-9c33-11eb-8d10-8d768f2ff3e5.PNG)
![2-4](https://user-images.githubusercontent.com/71058061/114490967-682d5a80-9c33-11eb-8b17-0141d2ddfa85.PNG)
![2-5](https://user-images.githubusercontent.com/71058061/114490968-695e8780-9c33-11eb-8270-458306cb668e.PNG)
![3-1](https://user-images.githubusercontent.com/71058061/114490971-69f71e00-9c33-11eb-9826-50880a2d6a8a.PNG)
![3-2](https://user-images.githubusercontent.com/71058061/114490974-6b284b00-9c33-11eb-9954-1278413930c2.PNG)
![3-3](https://user-images.githubusercontent.com/71058061/114490975-6bc0e180-9c33-11eb-8f94-21222c8e70fe.PNG)
![3-4](https://user-images.githubusercontent.com/71058061/114490976-6bc0e180-9c33-11eb-8f78-31011e887978.PNG)
![3-5](https://user-images.githubusercontent.com/71058061/114490977-6c597800-9c33-11eb-85c6-eb03058ed7b6.PNG)

# One More Thing
We need to edit two files(checkpost.ps1 & runpostlogin.ps1) before extracting then in req directory,<br>
![file1](https://user-images.githubusercontent.com/71058061/114493301-f277bd80-9c37-11eb-85de-2213400fd868.png)
![file2](https://user-images.githubusercontent.com/71058061/114493307-f4418100-9c37-11eb-8f1e-0b879fafc3e8.png)

Now Convert these files to .Exe by using Ps2Exe Tool (Official Microsoft Tool) from Powershell and copy them in req dir .<br>









