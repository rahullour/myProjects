class CreateUsersCheckouts < ActiveRecord::Migration[7.0]
  def change
    create_table :users_checkouts do |t|
      t.timestamps
    end
  end
end
