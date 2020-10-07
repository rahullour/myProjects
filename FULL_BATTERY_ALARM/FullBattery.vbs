set oLocator = CreateObject("WbemScripting.SWbemLocator")
set oServices = oLocator.ConnectServer(".","root\wmi")
set oResults = oServices.ExecQuery("select * from batteryfullchargedcapacity")
for each oResult in oResults
   iFull = oResult.FullChargedCapacity
next
'creating player obj
Dim oPlayer
Dim i
Dim count
Dim lcount
lcount=0
count=0

Set oPlayer = CreateObject("WMPlayer.OCX")
Set objShell = CreateObject("Wscript.Shell")
strPSCommand="setvol 50"
strDOSCommand = "PowerShell.exe -windowstyle hidden " & strPSCommand & ""

while (1)

   dim speechobject
set speechobject=createobject("sapi.spvoice")

    set oResults = oServices.ExecQuery("select * from batterystatus")
  for each oResult in oResults
    iRemaining = oResult.RemainingCapacity
    bCharging = oResult.Charging
  next
iPercent = ((iRemaining / iFull) * 100) mod 100
  if iPercent=0 then
   iPercent=100
   end if
    if bCharging=true and count=0 then
Set objExec = objShell.Exec(strDOSCommand)
 speechobject.speak "POWER CONNECTED"
 Wscript.sleep 1000
  count=1
  end if

 if bCharging=false and count=1 then
Set objExec = objShell.Exec(strDOSCommand)
 speechobject.speak "POWER DISCONNECTED"
Wscript.sleep 1000
   count=0
 end if
 
  if bCharging=true and (iPercent>98) Then
' msgbox "Battery is fully charged,disconnect me (:"
' Play audio
Set objExec = objShell.Exec(strDOSCommand)
  if bCharging=true Then
oPlayer.URL = "C:\Program Files\BATTERY_ALARM\glados_bat_full_2.mp3"
oPlayer.controls.play 
Wscript.sleep 3000
end if
end if


if bCharging=false and iPercent < 10 Then
Set objExec = objShell.Exec(strDOSCommand)
speechobject.speak "BATTERY Extremely LOW,PUT ON CHARGE USER"
   end if
       

    if bCharging=true and (iPercent >30)  Then
     lcount=0
    end if

    if bCharging=false and (iPercent =30) and lcount=0  Then
    msgbox "Battery is low,battery saver activated (:"
    lcount=1
    end if
  Wscript.sleep 1000
wend

 



