class AddPasswordEncrpCopyColumnToUser < ActiveRecord::Migration[7.0]
  def change
    add_column:users, :encrypted_password_copy, :string, :default => ""
  end
end
