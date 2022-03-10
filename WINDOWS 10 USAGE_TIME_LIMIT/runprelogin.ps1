 Get-WmiObject Win32_process -filter 'name = "runprelogin.exe"' | foreach-object { $_.SetPriority(256) } 
$currentday=[int](Get-Date).Day
$oldday=[int](Get-Content -Path C:\Windows\Logs\Windows\olddatedata.txt)


if($currentday -ne $oldday)
{
 
net user "Prashant" xenon@789
Set-Itemproperty -path 'HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System' -Name 'legalnoticecaption' -value 'Notice For Prashant :)'
Set-Itemproperty -path 'HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System' -Name 'legalnoticetext' -value 'Your Account Password Is Changed Back To xenon@789'
Clear-Content -Path C:\Windows\Logs\Windows\uptime.txt
Add-Content -Path C:\Windows\Logs\Windows\uptime.txt -Value 0
$currentday=(Get-Date).Day
Clear-Content -Path C:\Windows\Logs\Windows\olddatedata.txt
Add-Content -Path C:\Windows\Logs\Windows\olddatedata.txt -Value "$currentday"
Restart-Computer -Force


}


    