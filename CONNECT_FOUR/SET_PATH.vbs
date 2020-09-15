' VBSript BY RAHUL LOUR
Set objShell = CreateObject("WScript.Shell")
Set objEnv = objShell.Environment("System")


'Get the current value of Path
oldSystemPath = objEnv("PATH")

path1= "C:\CONNECT_FOUR\JAVAFX\bin"
path2="C:\Program Files\Java\jdk-14.0.1\bin"

foundstat1=InStr(oldSystemPath,path1)
foundstat2=InStr(oldSystemPath,path2)

If foundstat1=0 AND foundstat2=0 Then
newSystemPath = oldSystemPath & ";" & path1 & ";" & path2
'Set the new Path
objEnv("PATH") = newSystemPath
End If

If foundstat1>0 AND foundstat2=0 Then

newSystemPath = oldSystemPath & ";" & path2
'Set the new Path
objEnv("PATH") = newSystemPath
End If

If foundstat1=0 AND foundstat2>0 Then

newSystemPath = oldSystemPath & ";" & path1
'Set the new Path
objEnv("PATH") = newSystemPath
End If


