class UpdateUsers < ActiveRecord::Migration[7.0]
  def change
    add_column(:users, :provider, :string, limit: 30, null: false, default: '')
    add_column(:users, :uid, :string, limit: 500, null: false, default: '')
  end
end
