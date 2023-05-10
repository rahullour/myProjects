class ApplicationMailer < ActionMailer::Base
  default from: Rails.application.credentials.mail[:SENDMAIL_USERNAME]
  def contact_mail(contact_id,email)
    @contact_id = contact_id
    mail(to: email, subject: "OnShopSystem : Query Submission, Your Query ID: #{contact_id}")  
  end

  def contact_mail_admin(contact_id,email)
    @contact_id = contact_id
    mail(to: email, subject: "Query Submission, Users Query ID: #{contact_id}")  
  end

  def otp_mail(otp,email)
    @otp = otp
    mail(to: email, subject: "OnShopSystem : Otp For OnShopSystem")  
  end


  def pass_reset_complete(email)
    mail(to: email, subject: "OnShopSystem : Password reset complete")  
  end

end
