Set WshShell = CreateObject("WScript.Shell")

WshShell.Run chr(34) & "C:\CONNECT_FOUR\RunMeFirst.vbs" & Chr(34), 1

Set WshShell = Nothing