$currentday=(Get-Date).Day
Clear-Content -Path C:\Windows\Logs\Windows\olddatedata.txt
Add-Content -Path C:\Windows\Logs\Windows\olddatedata.txt -Value "$currentday"
$storedtime=[int](Get-Content -Path C:\Windows\Logs\Windows\uptime.txt)
$45=1
$85=1


$c = new-object -comobject wscript.shell
    $d = $c.popup("You Have "+(90-$storedtime)+" Minutes To Use Your Computer,Use Your Time Wisely Brother :)",5,"Notice",64)


if($storedtime-eq 45 -and $45-eq 1)
{
$a = new-object -comobject wscript.shell
    $b = $a.popup("You Have "+(90-$storedtime)+" Minutes Left To Use This Computer :)",5,"Notice",64)
    $45=0
    
}
if($storedtime-eq 88 -and $85-eq 1)
{
$e = new-object -comobject wscript.shell
    $f = $e.popup("You Have "+(90-$storedtime)+" Minutes Left To Use This Computer,You Are Advised To Save Your Ongoing Work :)",5,"Notice",64)
    $85=0
    
}


if($storedtime-eq 90)
{ 
Set-Itemproperty -path 'HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System' -Name 'legalnoticecaption' -value 'Notice For Prashant :)'
Set-Itemproperty -path 'HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System' -Name 'legalnoticetext' -value 'Your Account Is Now Locked, Try Login Tommorrow'
$g = new-object -comobject wscript.shell
    $h = $g.popup("Your Computer Is Locked And Will Unlock Tommorrow Now, Restart in 5 Sec :) ",5,"Notice",64)
net user "Prashant" adminra

Clear-Content -Path C:\Windows\Logs\Windows\uptime.txt
Add-Content -Path C:\Windows\Logs\Windows\uptime.txt -Value 0
Start-Sleep -s 5
Restart-Computer -Force
Get-Process | Where-Object {$_.MainWindowTitle -ne ""} | stop-process

}






while(1)
{ 

Start-Sleep -s 300
   
$vartime =[int](Get-Content -Path C:\Windows\Logs\Windows\uptime.txt)
Clear-Content -Path C:\Windows\Logs\Windows\uptime.txt
$vartime=$vartime+5
$newtime=$vartime

Add-Content -Path C:\Windows\Logs\Windows\uptime.txt -Value "$newtime"




if($newtime-eq 45 -and $45-eq 1)
{
$a = new-object -comobject wscript.shell
    $b = $a.popup("You Have "+(90-$newtime)+" Minutes Left To Use This Computer :)",5,"Notice",64)
    $45=0
    
}
if($newtime-eq 85 -and $85-eq 1)
{
$e = new-object -comobject wscript.shell
    $f = $e.popup("You Have "+(90-$newtime)+" Minutes Left To Use This Computer,You Are Advised To Save Your Ongoing Work :)",5,"Notice",64)
    $85=0
    
}


if($newtime-eq 90)
{ 
Set-Itemproperty -path 'HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System' -Name 'legalnoticecaption' -value 'Notice For Prashant :)'
Set-Itemproperty -path 'HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System' -Name 'legalnoticetext' -value 'Your Account Is Now Locked, Try Login Tommorrow'
$g = new-object -comobject wscript.shell
    $h = $g.popup("Your Computer Is Locked And Will Unlock Tommorrow Now, Restart in 5 Sec :) ",5,"Notice",64)
net user "Prashant" adminra

Clear-Content -Path C:\Windows\Logs\Windows\uptime.txt
Add-Content -Path C:\Windows\Logs\Windows\uptime.txt -Value 0
Start-Sleep -s 5
Restart-Computer -Force
Get-Process | Where-Object {$_.MainWindowTitle -ne ""} | stop-process

}

}