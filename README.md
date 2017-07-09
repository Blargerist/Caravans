---

*Caravans*

<details>
	<summary><code>mod.caravans.Caravan.registerCaravan(name)</code></summary>
	<samp>
		@param name (String) - Name of the caravan to be added, all caravans are registered under caravans:<name>
	</samp>
</details>

<details>
	<summary><code>mod.caravans.Caravan.addFollower(caravan, merchant)</code></summary>
	<samp>
		@param caravan (String) - Name of the caravan to add follower to. Caravan MUST be registered before calling this.
		@param merchant (String) - Name of merchant to add to the caravan. Merchant MUST be registered before calling this.
	</samp>
</details>


*Merchants*

<details>
	<summary><code>mod.caravans.Merchant.registerMerchant(name, icon)</code></summary>
	<samp>
		@param name (String) - Name of the merchant to be added, also used for the name of the tab in the gui, all merchants are registered under caravans:<name>
		@param icon (IItemStack) - Icon for this merchant's tab.
	</samp>
</details>

<details>
	<summary><code>mod.caravans.Merchant.addTrade(merchant, input, output)</code></summary>
	<samp>
		@param merchant (String) - Name of merchant to obtain this trade. Merchant MUST be registered before calling this.
		@param input (IItemStack) - ItemStack required to buy this trade.
		@param output (IItemStack) - ItemStack recieved through buying this trade.
	</samp>
</details>



---