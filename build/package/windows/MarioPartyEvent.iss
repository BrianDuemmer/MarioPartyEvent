;This file will be executed next to the application bundle image
;I.e. current directory will contain folder MarioPartyEvent with application files
[Setup]
AppId={{fxApplication}}
AppName=MarioPartyEvent
AppVersion=1.0
AppVerName=MarioPartyEvent 1.0
AppPublisher=TheDuemmer
AppComments=MarioPartyEvent
AppCopyright=Copyright (C) 2017
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={localappdata}\MarioPartyEvent
DisableStartupPrompt=Yes
DisableDirPage=No
DisableProgramGroupPage=Yes
DisableReadyPage=Yes
DisableFinishedPage=Yes
DisableWelcomePage=Yes
DefaultGroupName=TheDuemmer
;Optional License
LicenseFile=
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename=MarioPartyEvent-1.0
Compression=lzma
SolidCompression=yes
PrivilegesRequired=lowest
SetupIconFile=MarioPartyEvent\MarioPartyEvent.ico
UninstallDisplayIcon={app}\MarioPartyEvent.ico
UninstallDisplayName=MarioPartyEvent
WizardImageStretch=No
WizardSmallImageFile=MarioPartyEvent-setup-icon.bmp   
ArchitecturesInstallIn64BitMode=x64


[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "MarioPartyEvent\MarioPartyEvent.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "MarioPartyEvent\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\MarioPartyEvent"; Filename: "{app}\MarioPartyEvent.exe"; IconFilename: "{app}\MarioPartyEvent.ico"; Check: returnTrue()
Name: "{commondesktop}\MarioPartyEvent"; Filename: "{app}\MarioPartyEvent.exe";  IconFilename: "{app}\MarioPartyEvent.ico"; Check: returnFalse()


[Run]
Filename: "{app}\MarioPartyEvent.exe"; Parameters: "-Xappcds:generatecache"; Check: returnFalse()
Filename: "{app}\MarioPartyEvent.exe"; Description: "{cm:LaunchProgram,MarioPartyEvent}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\MarioPartyEvent.exe"; Parameters: "-install -svcName ""MarioPartyEvent"" -svcDesc ""MarioPartyEvent"" -mainExe ""MarioPartyEvent.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\MarioPartyEvent.exe "; Parameters: "-uninstall -svcName MarioPartyEvent -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
