class AddOtpToUsers < ActiveRecord::Migration[7.0]
  def change
    add_column :users, :phone_code, :string
  end
end
