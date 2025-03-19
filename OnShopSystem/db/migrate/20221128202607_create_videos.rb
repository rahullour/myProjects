class CreateVideos < ActiveRecord::Migration[7.0]
  def change
    create_table :videos do |t|
      t.string :title
      t.integer :para_word_count
      t.date :date_of_forecast
      t.timestamps
    end
  end
end
