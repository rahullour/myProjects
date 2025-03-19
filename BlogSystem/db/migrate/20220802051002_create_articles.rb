class CreateArticles < ActiveRecord::Migration[7.0]
  def change
    create_table :articles do |t|
      t.string :title
      t.text :des
      t.text :author

      t.timestamps
    end
  end
end
