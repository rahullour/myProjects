Set objShell = CreateObject("WScript.Shell")
Set objEnv = objShell.Environment("System")


'Get the current value of Path
oldSystemPath = objEnv("PATH")

path1= "C:\CONNECT_FOUR\JAVAFX\bin"
path2="C:\Program Files\Java\jdk-15.0.1\bin"

foundstat1=InStr(oldSystemPath,path1)
foundstat2=InStr(oldSystemPath,path2)
Set objShell = CreateObject("Shell.Application")
Set FSO = CreateObject("Scripting.FileSystemObject")
If foundstat1=0 OR foundstat2=0 Then

If FSO.FileExists("C:\CONNECT_FOUR\SET_PATH.VBS") Then
     objShell.ShellExecute "wscript.exe", _
        Chr(34)  & "C:\CONNECT_FOUR\SET_PATH.VBS" & Chr(34), "", "runas", 1
Else
     MsgBox "Script file SET_PATH.VBS not found"
End If
End If





 